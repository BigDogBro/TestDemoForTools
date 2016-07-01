package performanceTestingI;

public interface BatteryTestI {
	
	/**
	 * 
	 * @description: 清除电池历史信息
	 * @date: 2016年4月13日 下午3:26:03
	 * @author: wenli
	 */
	public void clear();
	
	/**
	 * 
	 * @description: 获得预估的耗电信息 adb shell dumpsys batterystats pckName 找到computed drain一行
	 * @date: 2016年4月13日 下午3:26:41
	 * @author: wenli
	 * @param pckName
	 * @return
	 */
	public String getBatteryInfo_computed(String pckName);
	
	/**
	 * 
	 * @description: 获得实际的耗电信息 adb shell dumpsys batterystats pckName 找到actual drain一行
	 * @date: 2016年4月13日 下午3:27:41
	 * @author: wenli
	 * @param pckName
	 * @return
	 */
	public String getBatteryInfo_actual(String pckName);
}
