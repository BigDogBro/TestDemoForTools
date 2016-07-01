package testcase;

import java.io.IOException;
import java.sql.SQLException;

import performanceTestingI.StartTimeTestI;
import performanceTestingImpl.StartTimeTestImpl;
import android.os.RemoteException;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.meizu.automation.Expectation;
import com.meizu.automation.Steps;
import com.meizu.automation.constants.AutoException;
import com.meizu.test.util.AppInfo;
import com.meizu.test.util.ShellUtil;
import com.meizu.uibridge.NotRootException;

public class startTest extends AutoBase{
	
private StartTimeTestI st=new StartTimeTestImpl();


   public static String app= AppInfo.PACKAGE_BROWSER;
   public static String activity=AppInfo.ACTIVITY_BROWSER;
   

	@Steps("冷启动/热启动启动时间")
    @Expectation("600")
    public void test01() throws IOException,AutoException,UiObjectNotFoundException, NotRootException, RemoteException, SQLException, InterruptedException {   
		
		
		
		ShellUtil.startApp(app, activity);
		
		//冷启动时间
		int runTime=6;
		 System.out.println("------冷启动--------");
		for (int i = 0; i < runTime; i++) {			
			this.sleep(2000);
			ShellUtil.exitApp(app);
			this.sleep(1000);
			System.out.println(st.getStartTime(app+"/"+activity));
		}
		
		
        System.out.println("-----热启动--------");
		//热启动时间
		for (int i = 0; i < runTime; i++) {
			this.sleep(2000);
			//ShellUtil.clearCache(app);
			this.pressHome();
			this.sleep(1000);
			System.out.println(st.getStartTime(app+"/"+activity));
		}
	}

}
