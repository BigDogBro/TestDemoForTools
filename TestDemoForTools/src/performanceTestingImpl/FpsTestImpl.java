package performanceTestingImpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.meizu.test.util.ShellUtil;

import performanceTestingI.FpsTestI;
import util.Log;
import util.ShellUtils;


public class FpsTestImpl extends Thread implements FpsTestI{
	
	private static final DecimalFormat df = new DecimalFormat("######0.00");   

	private static ExecutorService sThreadPool = Executors. newSingleThreadExecutor();
	
	//存储每次执行的frames的处理时间戳数据
	private static ArrayList<String> sFrameTimestampslist = new ArrayList<String>();
	
	private static ArrayList<String> sFpsList = new ArrayList<String>();
	
	private String sPackageName;
	private String sActivityName;
	
	@Override
	public void start(String sPackageName , String sActivityName) {
		// TODO Auto-generated method stub
		FpsTestImpl instance = new FpsTestImpl();
		instance.setsPackageName(sPackageName);
		instance.setsActivityName(sActivityName);
		sThreadPool.execute(instance);
	}
 
	@Override
	public void close() {
		// TODO Auto-generated method stub
		sThreadPool.shutdownNow();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		reflash();
		reflash();
		reflash();	
		
		while(true){
			try {
				ShellUtil.exec("dumpsys SurfaceFlinger --latency-clear");
				if (sPackageName != null && sActivityName != null) 
					getFpsInfo(sPackageName, sActivityName);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}	
		}
	}
	
	public void getFpsInfo(String packageName , String activityName) {
		
		double fps = 0.0;
		
		//每次获取先清空数据
		sFrameTimestampslist.clear();
		
		try {
			
			//执行adb命令获取gpu处理每帧图像的时间戳
			//adb shell dumpsys SurfaceFlinger --latency com.android.browser/com.android.browser.BrowserActivity 
			ShellUtils.CommandResult result = ShellUtils.execCommand("dumpsys SurfaceFlinger --latency " + packageName + "/" + activityName, true);
			String[] resultStrings = result.successMsg.split("\n");
			
			for (int i = 1; i < resultStrings.length; i++) {	
				//不处理"0       0       0"
				if (resultStrings[i].length() > "0       0       0".length() && resultStrings[i] != "") {
					String[] Lines = resultStrings[i].split("	");
					for (String string : Lines) 
						sFrameTimestampslist.add(string);				
				}
			}
				
			if (sFrameTimestampslist.size() > 0) {
				fps = (double)( (sFrameTimestampslist.size()/3) * Math.pow(10, 9)/(Long.parseLong(sFrameTimestampslist.get(sFrameTimestampslist.size() - 1)) - Long.parseLong(sFrameTimestampslist.get(0))));
			}		
			
			//打印出fps信息方便调试
			Log.I("fps:" + fps);
			sFpsList.add(df.format(fps));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public void reflash() {
		//清空数据缓冲区
		ShellUtil.exec("dumpsys SurfaceFlinger --latency-clear");
	}

	@Override
	public ArrayList<String> getFpsList() {
		// TODO Auto-generated method stub
		return sFpsList;
	}	
	
	public void setsActivityName(String sActivityName) {
		this.sActivityName = sActivityName;
	}
	public void setsPackageName(String sPackageName) {
		this.sPackageName = sPackageName;
	}

	
	
}
