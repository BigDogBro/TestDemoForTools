package performanceTestingI;

import java.util.ArrayList;

public interface FpsTestI {
	
	/**
	 * 
	 * @description: 开始调用线程获取fps
	 * @date: 2016年4月13日 下午3:29:33
	 * @author: wenli
	 * @param sPackageName
	 * @param sActivityName
	 */
	public void start(String sPackageName , String sActivityName);
	
	/**
	 * 
	 * @description: 执行完，关闭线程 
	 * @date: 2016年4月13日 下午8:44:45
	 * @author: wenli
	 */
	public void close();
	
	/**
	 * 
	 * @description: 获得从start到close之间的fps数据，默认保存为2位小数 
	 * @date: 2016年4月13日 下午8:45:02
	 * @author: wenli
	 * @return
	 */
	public ArrayList<String> getFpsList();
	
}
