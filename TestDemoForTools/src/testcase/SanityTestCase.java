package testcase;

import java.io.IOException;

import performanceTestingI.BatteryTestI;
import performanceTestingI.FpsTestI;
import performanceTestingI.MemoryTestI;
import performanceTestingImpl.BatteryTestImpl;
import performanceTestingImpl.FpsTestImpl;
import performanceTestingImpl.MemoryTestImpl;
import util.ExcelUtil;
import util.Log;
import android.os.RemoteException;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.meizu.automation.Expectation;
import com.meizu.automation.Steps;
import com.meizu.automation.constants.AutoException;
import com.meizu.test.util.AppInfo;
import com.meizu.test.util.ShellUtil;
import com.meizu.uibridge.NotRootException;


public class SanityTestCase extends AutoBase{
		
	private FpsTestI fpsTestExample = new FpsTestImpl();
	private BatteryTestI batteryTestExample = new BatteryTestImpl();
	private MemoryTestI memoryTestExample = new MemoryTestImpl();
	
	//MZ在资讯流的滑动场景
	@Steps("Three Performance Test Demo - Fps Test(MZ)")
    @Expectation("Run smoothly")
    public void MZFpsTestDemo() throws IOException,AutoException,UiObjectNotFoundException, NotRootException, RemoteException {         		     		 
		
		this.sleep(3000);
		Log.I("Enter Browser.");	
		
		fpsTestExample.start(AppInfo.PACKAGE_BROWSER,AppInfo.ACTIVITY_BROWSER);
		Log.I("Start FpsTest.");
		
		Log.I("");
		Log.I("SwipeUp 40s");

		long beginTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - beginTime < 50000) {
			this.swipeUp(10);
		}
		
		Log.I("");
		Log.I("SwipeDown 30s");
		beginTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - beginTime < 30000) {
			this.swipeDown(10);
		}
		
		fpsTestExample.close();
		Log.I("");
		Log.I("End FpsTest.");
		
		this.sleep(2000);
		
		Log.I("");
		Log.I("FpsList Infomation:");
		for (int i = 0; i < fpsTestExample.getFpsList().size(); i++) {
			Log.I("fpsList" + i + ": " + fpsTestExample.getFpsList().get(i));
			}
		
		ExcelUtil.add("fps", fpsTestExample.getFpsList());

	}
	
	
	//MZ在资讯流的滑动场景
	@Steps("Three Performance Test Demo - Fps Test(UC)")
    @Expectation("Run smoothly")
    public void UCFpsTestDemo() throws IOException,AutoException,UiObjectNotFoundException, NotRootException, RemoteException {         		     		 
		
		this.sleep(3000);
		Log.I("Enter Browser.");	
		
		fpsTestExample.start("com.UCMobile","com.uc.browser.InnerUCMobile");
		Log.I("Start FpsTest.");
		
		Log.I("");
		Log.I("SwipeUp 40s");

		long beginTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - beginTime < 50000) {
			this.swipeUp(10);
		}
		Log.I("");
		Log.I("SwipeDown 30s");
		beginTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - beginTime < 30000) {
			this.swipeDown(10);
		}
		
		fpsTestExample.close();
		Log.I("");
		Log.I("End FpsTest.");
		
		this.sleep(2000);
		
		Log.I("");
		Log.I("FpsList Infomation:");
		for (int i = 0; i < fpsTestExample.getFpsList().size(); i++) {
			Log.I("fpsList" + i + ": " + fpsTestExample.getFpsList().get(i));
		}
		
	}
	
	
	@Steps("Three Performance Test Demo - Battery Test(MZ)")
    @Expectation("Run smoothly")
    public void MZBatteryTest() throws IOException,AutoException,UiObjectNotFoundException, NotRootException, RemoteException {   
		
		batteryTestExample.clear();
		Log.I("Reset BatteryInfo");
		
		Log.I("Enter Browser");
		this.sleep(3000);
		
		//进入资讯流
		this.swipeUp(20);
		
		//模拟用户行为使用浏览器浏览一个小时
		long beginTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - beginTime < 600000) {
			
			//点击进入新闻页面
			this.sleep(1000);
			UiCollection v = new UiCollection(new UiSelector().resourceId("com.android.browser:id/article_list"));	
			v.getChildByInstance(new UiSelector().className("android.widget.LinearLayout"), 2).click();
			
			this.sleep(2000);
			
			if (((int)(1+Math.random()*(3-1+1))) == 1) {
				//情况1 - 正常速度滑动一段时间便退出
				int temp = (int)(1+Math.random()*(5-1+1));
				for (int i = 0; i < temp; i++){			
					this.swipeUp(((int)(1+Math.random()*(3-1+1))) * 10);
					this.sleep(1000);
				}
				this.getUiDevice().pressBack();
			} else {
				//情况2 - 滑动较长时间
				int temp = (int)(5+Math.random()*(10-5+1));
				for (int i = 0; i < temp; i++){			
					this.swipeUp(((int)(1+Math.random()*(3-1+1))) * 10);
					this.sleep(1000);
				}
				this.getUiDevice().pressBack();
			}
			
			//下滑动作
			this.sleep(1000);
			int temp = (int)(1+Math.random()*(5-2+1));
			for (int i = 0; i < temp; i++) {
				this.swipeUp(((int)(1+Math.random()*(3-1+1))) * 10);
				this.sleep(1000);
			}		
		}
		
		String batteryInfo_computed = batteryTestExample.getBatteryInfo_computed("com.android.browser");
		Log.I("BatteryInfo_computed is:" + batteryInfo_computed);
		String batteryInfo_actual = batteryTestExample.getBatteryInfo_actual("com.android.browser");
		Log.I("BatteryInfo_actual is:" + batteryInfo_actual);
		
		
		
	}
	
	@Steps("Three Performance Test Demo - Battery Test(UC)")
    @Expectation("Run smoothly")
    public void UCBatteryTest() throws IOException,AutoException,UiObjectNotFoundException, NotRootException, RemoteException {   
		
		batteryTestExample.clear();
		Log.I("Reset BatteryInfo");
		
		Log.I("Enter Browser");
		this.sleep(3000);
		
		//进入资讯流
		this.swipeUp(20);
		
		//模拟用户行为使用浏览器浏览一个小时
		long beginTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - beginTime < 600000) {
			
			//点击进入新闻页面
			this.sleep(1000);
			UiCollection v = new UiCollection(new UiSelector().className("android.widget.ListView"));	
			v.getChildByInstance(new UiSelector().className("android.widget.LinearLayout"), 2).click();
			
			this.sleep(2000);
			
			if (((int)(1+Math.random()*(3-1+1))) == 1) {
				//情况1 - 正常速度滑动一段时间便退出
				int temp = (int)(1+Math.random()*(5-1+1));
				for (int i = 0; i < temp; i++){			
					this.swipeUp(((int)(1+Math.random()*(3-1+1))) * 10);
					this.sleep(1000);
				}
				this.getUiDevice().pressBack();
			} else {
				//情况2 - 滑动较长时间
				int temp = (int)(5+Math.random()*(10-5+1));
				for (int i = 0; i < temp; i++){			
					this.swipeUp(((int)(1+Math.random()*(3-1+1))) * 10);
					this.sleep(1000);
				}
				this.getUiDevice().pressBack();
			}
			
			//下滑动作
			this.sleep(1000);
			int temp = (int)(1+Math.random()*(5-2+1));
			for (int i = 0; i < temp; i++) {
				this.swipeUp(((int)(1+Math.random()*(3-1+1))) * 10);
				this.sleep(1000);
			}		
		}
		
		String batteryInfo = batteryTestExample.getBatteryInfo_computed("com.UCMobile");
		Log.I("BatteryInfo is:" + batteryInfo);
		String batteryInfo_actual = batteryTestExample.getBatteryInfo_actual("com.UCMobile");
		Log.I("BatteryInfo_actual is:" + batteryInfo_actual);
	}
	
	@Steps("Three Performance Test Demo - Memory Test")
    @Expectation("Run smoothly")
    public void MZMemoryTest() throws IOException,AutoException,UiObjectNotFoundException, NotRootException, RemoteException {   
		
		Log.I("Enter Browser");
		this.sleep(3000);
		
		printMemInfo();
		
		this.sleep(1000);
		for (int i = 1; i <= 5; i++) {
			
			this.getUiDevice().pressBack();
			this.sleep(3000);
			Log.I("press back.");
			printMemInfo();
			Log.I("");
			ShellUtil.startApp(AppInfo.PACKAGE_BROWSER, AppInfo.ACTIVITY_BROWSER);	
			if (i != 5) {
				this.sleep(3000);
				Log.I("restart browser.");
				printMemInfo();
			}
		}
		
		Log.I("Release Unneeded Memory Test");
		Log.I("");
		
		this.clickByText("百度");
		this.sleep(3000);
		this.pressBack();
		
		this.clickByText("京东");
		this.sleep(3000);
		this.pressBack();
		
		this.sleep(1000);
		printMemInfo();
		ShellUtil.exec("dumpsys gfxinfo com.android.browser -cmd trim 5");
		ShellUtil.exec("dumpsys gfxinfo com.android.browser -cmd trim 5");
		ShellUtil.exec("dumpsys gfxinfo com.android.browser -cmd trim 5");
		Log.I("");
		Log.I("After Release:");
		Log.I("");
		printMemInfo();
		
	}
	
	
	
	/*@Steps("Test Excel1")
    @Expectation("Run smoothly")
    public void testMZTestExcel() throws IOException,AutoException,UiObjectNotFoundException, NotRootException, RemoteException {   
		
		Log.I("Enter Browser");
		this.sleep(3000);
		
		Log.I("Begin:" + System.currentTimeMillis());
		Map<String, ArrayList<String>> instance = new HashMap<String, ArrayList<String>>();
		ArrayList<String> values = new ArrayList<String>();
		values.add("1111");
		values.add("2222");
		instance.put("hello", values);
		instance.put("hello2", values);
		ExcelUtil.writeExcel(instance);
		Log.I("End:" + System.currentTimeMillis());
	}
	
	@Steps("Test Excel2")
    @Expectation("Run smoothly")
    public void testMZTestExceqqqqqq() throws IOException,AutoException,UiObjectNotFoundException, NotRootException, RemoteException {   
		
		Log.I("Enter Browser");
		this.sleep(3000);
		
		Log.I("Begin:" + System.currentTimeMillis());
		Map<String, ArrayList<String>> instance = new HashMap<String, ArrayList<String>>();
		ArrayList<String> values = new ArrayList<String>();
		values.add("1111");
		values.add("2222");
		instance.put("hello2222", values);
		instance.put("hello22222", values);
		ExcelUtil.writeExcel(instance);
		Log.I("End:" + System.currentTimeMillis());
	}*/
	
	@Steps("xxxxx")
    @Expectation("xxxxx")
    public void test() throws IOException,AutoException,UiObjectNotFoundException, NotRootException, RemoteException {   
		
		exec("ime set com.android.adbkeyboard/.AdbIME");
		
		ShellUtil.clearCache("com.sta.zexuan.monkey");
		this.startApp("com.sta.zexuan.monkey", ".MainActivity");
		this.sleep(3000);
		
		//设置参数
		this.click("com.sta.zexuan.monkey:id/algo");
		this.clickByText("静音");
		
		this.click("com.sta.zexuan.monkey:id/algo");
		this.clickByText("锁定Wifi ");
		
		this.click("com.sta.zexuan.monkey:id/algo");
		this.clickByText("记忆模式");
		
		this.click("com.sta.zexuan.monkey:id/edt_duration");
		this.sleep(2000);
		this.click("com.sta.zexuan.monkey:id/self_test_et_duration_hour");
		this.setTextById("com.sta.zexuan.monkey:id/self_test_et_duration_hour", "12");
		this.sleep(2000);
		this.clickByText("确定");
		
		this.click("com.sta.zexuan.monkey:id/edt_package");
		this.clickByText("com.android.browser");
		this.clickByText("确定");
		
		this.setTextById("com.sta.zexuan.monkey:id/edt_delay", "500");
		
		this.clickByText("出现异常后继续执行Monkey测试");
		this.clickByText("出现无响应后继续执行Monkey测试");
		
		this.clickByText("开始测试");
		
	}
	
	public void printMemInfo(){
		memoryTestExample.start("com.android.browser");
		Log.I("Total is : " + memoryTestExample.getTOTAL());
		Log.I("Activities are :" + memoryTestExample.getActivites());
		Log.I("Views are :" + memoryTestExample.getViews());
	}
	
}
