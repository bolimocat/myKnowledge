//浏览已经创建的canal信息
package canalGuiTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.autotestBridge.function.checkBridgeMysql;
import com.autotestBridge.function.commonkit;
import com.autotestBridge.resource.resource;

public class canalViewProcess extends resource{

	private String Url;
//	private int ID;
	private String canal_name;
	commonkit kit = new commonkit();
	checkBridgeMysql checkMysql = new checkBridgeMysql("172.16.110.33","root","root123","3306","otter");
	
	
	public canalViewProcess(String url,String name){
		Url = url;
//		ID = id;
		canal_name = name;
	}
	
	/**
	 * 
	 * @throws InterruptedException
	 */
	public void viewCannl() throws InterruptedException {
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
	   		
	   	 //cannal配置开始
		      WebElement configure_management = driver.findElement(By.linkText("配置管理"));
		      Actions action = new Actions(driver);
		      action.clickAndHold(configure_management).perform();
		      
		      WebElement canalconfig = driver.findElement(By.linkText("canal配置"));
		      canalconfig.click();
		      Thread.sleep(2000);
		      
		      Thread.sleep(STSLEEP);
		      
		      //从已知cannal名称获得id，进入该条记录的详情。
		      int canalId = checkMysql.canalId(canal_name);
		      if(canalId==0) {
		    	  System.out.println("该条canal还没有被创建！");
		      }
		      else {
		    	  driver.navigate().to(BASEURL+"/canal_info.htm?canalId="+canalId);
			        Thread.sleep(5000);
			        //截图
		      }
		      
		      Thread.sleep(STSLEEP);
		      driver.quit();
	}

}
