package testcase;

import java.io.File;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.meizu.automation.Expectation;
import com.meizu.automation.Steps;
import com.meizu.automation.constants.AutoException;

public class TestAd16 extends AutoBase{
	/**
	 * @description: 测试广告屏蔽以及内核问题，主要覆盖16个主流网站
	 * @date: 2016年6月30日 下午2:17:16
	 * @author: wenli
	 * @throws AutoException 
	 * @throws UiObjectNotFoundException 
	 */
	
	@Steps("Ad.Test")
    @Expectation("Run smoothly")
    public void MZAdTestDemo() throws AutoException, UiObjectNotFoundException {     
		
		String urls = "m.baidu.com;m.toutiao.com;image.baidu.com;zhidao.baidu.com;info.3g.qq.com;"
				+ "tieba.baidu.com;wapwenku.baidu.com;wap.sougou.com;wk.baidu.com;m.souhu.com;sina.com.cn;3g.163.com;www.baidu.com;sports.sina.cn;wapbaike.baidu.com;m.hao123.com";
		/*String urls = "tieba.baidu.com;wapwenku.baidu.com;wap.sougou.com;wk.baidu.com;m.souhu.com;sina.cn;3g.163.com;www.baidu.com;sports.sina.cn;wapbaike.baidu.com;m.hao123.com";*/
		//String urls = "m.baidu.com";
		String[] urlStrings = urls.split(";");
		
		for (String url : urlStrings) {
			try {
				
				inputUrl(url);
				
				clickReject();
				
				//词条需要特别点击
				//  贴吧需要检查滑动?贴吧需要点击继续浏览
				//  今日头条需要进入首页
				//  sohu进去有问题,会提示是否打开输入法
				//  新浪点击了会进入客户端，偶现
				//  网易一进入会有一个大广告弹出
				//  新浪体育的广告也太夸张了 
				//点击百度糯米会跳转到app界面去
				if (url.equals("m.toutiao.com")) {
					this.clickByDesc("进入网页");
				} 
				if (url.equals("m.souhu.com")) {
					this.clickByText("阻止");
				}
				
				String headString = this.findElement("com.android.browser:id/url").getText();
				
				this.sleep(3000);
				UiCollection widgets = new UiCollection(new UiSelector().className("android.webkit.WebView"));
				
				int count = widgets.getChildCount(new UiSelector().className("android.view.View"));
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 5; j++) {
						UiObject randomWidget = widgets.getChildByInstance(new UiSelector().className("android.view.View"), (int)(Math.random()*(count - 1)));
						System.out.println(i + "_" + j);
						if (ifInThisWindow(randomWidget)) {
							 
							randomWidget.click();
							this.sleep(3000);
							String headStringAfterJump = "";
							if (this.isExistById("com.android.browser:id/url")) {
								headStringAfterJump = this.findElement("com.android.browser:id/url").getText();
							} else {
								headStringAfterJump = headString;
							}
							
							if (headStringAfterJump.equals(headString)) {
								File file = new File("/sdcard/hello/" + url + "_" + i + "_" + j + "_" + randomWidget.getContentDescription() + ".png");
								this.getUiDevice().takeScreenshot(file);
							} else {
								this.pressBack();
							}
						} else {
							j--;
						}
					}
					this.swipeUp(20);
					this.sleep(2000);
					this.swipeUp(20);
				}
				
				this.click("com.android.browser:id/home");
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	public void inputUrl(String url) throws AutoException {
		
		if (this.isExistById("com.android.browser:id/search_hint")) {
			this.click("com.android.browser:id/search_hint");
		} else if (this.isExistById("com.android.browser:id/url")) {
			this.click("com.android.browser:id/url");
		}
		
		if (this.isExistById("com.android.browser:id/clear")) {
			this.click("com.android.browser:id/clear");
		}
		
		this.setTextById("com.android.browser:id/url", url);
		
		this.getUiDevice().pressEnter();
		
		this.sleep(3000);
		
	}
	
	public void clickReject() throws AutoException{
		this.sleep(1000);
		if (this.isExistByText("拒绝")) {
			this.clickByText("拒绝");
		}
	}
	
	public boolean ifInThisWindow(UiObject uiObject) throws UiObjectNotFoundException, AutoException{
		int bottom = this.findElementByClassName("com.meizu.android.webkit.WebView").getBounds().bottom;
		if (uiObject.getBounds().left > 0 && uiObject.getBounds().bottom < bottom) 
			return true;
		else 
			return false;
	}
	
	public boolean ifUrlOrNot(UiObject uiObject) throws UiObjectNotFoundException{
		longClick(uiObject.getBounds().centerX(), uiObject.getBounds().centerY());
		this.sleep(1000);
		if (this.isExistByText("新窗口打开")) {
			this.pressBack();
			return true;
		}else {
			return false;
		}
	}
	
	public boolean ifJump() {
		return true;
	}
	
}
