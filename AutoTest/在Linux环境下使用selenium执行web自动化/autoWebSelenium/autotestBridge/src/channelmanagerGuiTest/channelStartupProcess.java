//启动一个指定的channel
package channelmanagerGuiTest;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.autotestBridge.function.checkBridgeMysql;
import com.autotestBridge.function.commonkit;

public class channelStartupProcess {
	private String Url;
	commonkit kit = new commonkit();
	checkBridgeMysql checkMysql = new checkBridgeMysql("172.16.110.33","root","root123","3306","otter");
	
	
	public channelStartupProcess(String url) {
		Url = url;
	}
	
	/**
	 * 启动Channel
	 * @throws InterruptedException
	 */
	public void startupChannel() throws InterruptedException {
			WebDriver driver = new FirefoxDriver(); 
		   driver.get(Url);
		   driver.manage().window().maximize();
		   
		   //登录开始
		   WebElement login_name =  driver.findElement(By.name("_fm.l._0.n"));
	       login_name.sendKeys("admin");
	       WebElement login_pass =  driver.findElement(By.name("_fm.l._0.p"));
	       login_pass.sendKeys("admin");
	       WebElement login_btn = driver.findElement(By.className("login_btn"));
	          login_btn.click();
	         
	   		Thread.sleep(2000);
	   	
	        //登录结束
	   		
	   		
	   		//启动channel
	   	 //同步配置开始
		      WebElement sync_management = driver.findElement(By.linkText("同步管理"));
		      Actions action = new Actions(driver);
		      action.clickAndHold(sync_management).perform();
		      
		      //启动channel --固定第一个位置
		      WebElement startupChannel = driver.findElement(By.xpath("//a[2]/span"));
		      startupChannel.click();
		      Thread.sleep(500);
		      	
		      	//打开对话框，接受。
		      Alert alertStartupChannel = driver.switchTo().alert();
		      Thread.sleep(500);
		        System.out.println(alertStartupChannel.getText());
		        Thread.sleep(500);
		        alertStartupChannel.accept();
		        Thread.sleep(2000);
		     
		      
		        driver.quit();
	}
}
