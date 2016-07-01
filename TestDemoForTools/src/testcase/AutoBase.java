package testcase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.ExcelUtil;
import util.Log;

import com.meizu.automation.AutoWatcher;
import com.meizu.test.common.AutoAllInOneTestCase;
import com.meizu.test.util.AppInfo;
import com.meizu.test.util.ShellUtil;

public class AutoBase extends AutoAllInOneTestCase{
	
	private static SimpleDateFormat currenttime=new SimpleDateFormat("yyyyMMddHHmm");	
	private static String REPORTPATH="R_"+currenttime.format(new java.util.Date());
	private static int restartTimes = 1;
	private static int failAndRestartTimes = 1;
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		
		Log.I(df.format(new Date()));
	
		watcher();
		
		this.pressHome();
		
		ExcelUtil.setExcelReportPath("/sdcard/" + REPORTPATH + "/performanceTestData.xls");
		
		File reportDir =new File("/sdcard/"+REPORTPATH); 
		if (!reportDir.exists()) 
			reportDir.mkdir();
		
		if (this.getName().contains("UC")) {
			//adb shell dumpsys activity top|findstr ACTIVITY
			ShellUtil.clearCache("com.UCMobile");
			ShellUtil.startApp("com.UCMobile", "com.uc.browser.InnerUCMobile");
			prepare();
		}else {
			ShellUtil.clearCache(AppInfo.PACKAGE_BROWSER);
			ShellUtil.startApp(AppInfo.PACKAGE_BROWSER, AppInfo.ACTIVITY_BROWSER);
			prepare();
		}
		
		
	}
	
	@Override
	protected void runTest() throws Throwable{
		// TODO Auto-generated method stub
		try {
			Log.I("************ " + this.getName() + " Start ************");
			super.runTest();
			Log.I("************ " + this.getName() + " Finsh ************");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if (failAndRestartTimes > 0) {
				Log.I("FailAndRestart:");
				failAndRestartTimes--;
				setUp();
				runTest();
			}

		}
	}
	
	@Override
	protected void tearDown() {
		// TODO Auto-generated method stub
		Log.I("");
		Log.I("Exit Browser");
		Log.I(df.format(new Date()));		
		
		//如果在testcase没写进excel，结束的时候可以统一写进excel
		if (!ExcelUtil.getTitleAndValueList().isEmpty()) {
			ExcelUtil.writeExcel();
			ExcelUtil.getTitleAndValueList().clear();
		}
			
		try {
			super.tearDown();
			this.pressHome();
			if (this.getName().contains("UC")) 
				ShellUtil.exitApp("com.UCMobile");
			else
				ShellUtil.exitApp(AppInfo.PACKAGE_BROWSER); 
			File report = new File("/sdcard/" + REPORTPATH + "/runlog.txt");
			if (!report.exists()) {
				report.createNewFile();
			}
			Log.writeLog(report);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void watcher(){
		this.registerWatcher("判断", new AutoWatcher() {
			
			@Override
			public boolean checkForCondition() {
				// TODO Auto-generated method stub
				try {
					if (isExistByText("确定")) {
						clickByText("确定");
					} else if (isExistByText("取消")) {
						clickByText("取消");
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}	
				return false;
			}
		});
	}
	
	public void prepare() throws Exception{	
		
		Log.I("Judging.");
		this.sleep(3000);
		
		if (this.waitForText("忽略此版本"))
			this.clickByText("忽略此版本");
		
		if (this.waitForText("允许"))
			this.clickByText("允许");
		
		if (this.getName().contains("UC")) {
			this.sleep(10000);
			this.click(545, 1830);
			this.waitForText("不, 谢谢");
			if (this.isExistByText("不, 谢谢"))
				this.clickByText("不, 谢谢");
			/*
			if (this.isExistByText("百度", 5000)) {
				Log.I("Enter Success.");
			}else {
				if (restartTimes > 0) {
					restartTimes--;
					Log.I("Restart App.");
				    setUp();
				}
			}*/
		} else {
			if (this.isExistByText("百度", 5000)) {
				Log.I("Enter Success.");
			}else {
				if (restartTimes > 0) {
					restartTimes--;
					Log.I("Restart App.");
				    setUp();
				}
			}
		}
	}
	
	
	
}
