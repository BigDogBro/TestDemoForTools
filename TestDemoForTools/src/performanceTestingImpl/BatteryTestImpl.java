package performanceTestingImpl;

import com.meizu.test.util.ShellUtil;

import performanceTestingI.BatteryTestI;
import util.ShellUtils;


public class BatteryTestImpl implements BatteryTestI{

	private String batteryInfo_computed;
	private String batteryInfo_actual;
	
	public void BaterryInfo(String pckName) {
		ShellUtils.CommandResult result = ShellUtils.execCommand("dumpsys batterystats " + pckName, true);
		String[] resultStrings = result.successMsg.split("\n");
		for (String line : resultStrings) {
			if (line.contains("Computed drain:")) {
				batteryInfo_computed = line.substring(line.indexOf("Computed drain:") + "Computed drain:".length() , line.indexOf(", actual drain:")).trim();
				batteryInfo_actual = line.substring(line.indexOf(", actual drain:") + ", actual drain:".length() ).trim();
				break;
			}
		}
	}
	
	public void clear() {
		ShellUtil.exec("dumpsys batterystats --reset");
	}
	
	@Override
	public String getBatteryInfo_computed(String pckName) {
		// TODO Auto-generated method stub
		BaterryInfo(pckName);
		return this.batteryInfo_computed;
	}
	
	@Override
	public String getBatteryInfo_actual(String pckName) {
		// TODO Auto-generated method stub
		BaterryInfo(pckName);
		return this.batteryInfo_actual;
	}
	
}
