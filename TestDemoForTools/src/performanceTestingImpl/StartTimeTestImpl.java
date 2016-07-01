package performanceTestingImpl;



import com.meizu.test.util.ShellUtil;

import performanceTestingI.StartTimeTestI;
import util.ShellUtils;
import util.ShellUtils.CommandResult;

public class StartTimeTestImpl implements StartTimeTestI{

	@Override
	public String getStartTime(String PackageActivity) {
		
		 String[] packageName=PackageActivity.split("//");
		 ShellUtil.exitApp(packageName[0]);
		 CommandResult result=  ShellUtils.execCommand(
					"am start -W "+PackageActivity+"", false);		  
		 String response=result.successMsg;
		 //adb格式打印是固定的
		 String time=response.substring(response.indexOf("WaitTime")+10,response.indexOf("WaitTime")+14).trim();
		return time;
	}

}
