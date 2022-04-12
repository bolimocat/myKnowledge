package pipelinemanagerGuiTest;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.autotestBridge.dom.dbLink;
import com.autotestBridge.function.checkBridgeMysql;
import com.autotestBridge.function.commonkit;
import com.autotestBridge.resource.resource;
import com.autotestBridge.testcase.dom.caseChannelBean;
import com.autotestBridge.testcase.dom.casePipelineBean;
import com.autotestBridge.testcase.function.caseImplement;

public class pipelineTestProcess extends resource{
	private String Url;
	private int ID;
	private String channel_name;
	commonkit kit = new commonkit();
	checkBridgeMysql checkMysql = new checkBridgeMysql("172.16.110.33","root","root123","3306","otter");
	
	public pipelineTestProcess(String url,int id,String name) {
		Url = url;
		ID = id;
		channel_name = name;
	}
	
	

	/**
	 * 根据pipeline名称，查看具体内容。
	 * @throws InterruptedException
	 */
	public void viewPipeline() throws InterruptedException{
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
			      
			      //根据channel的id，进入pipeline列表页
			      int channelId = checkMysql.channelId("测试用的channel");
			      driver.navigate().to(BASEURL+"/add_pipeline.htm?channelId="+channelId);
			      
			      //根据pipeline的名称，进入查看页面。
			      if(checkMysql.havePip(channelId)) {
				      driver.navigate().to(BASEURL+"/add_pipeline.htm?channelId="+channelId);
				      Thread.sleep(2000);
//				      <input type="radio" name="_fm.pi._0.h" value="true" id="RadioGroup1_0" class="radio"/>
				      WebElement test = driver.findElement(By.name("_fm.pi._0.h")).findElement(By.id("RadioGroup1_0"));
				      	test.click();
				      	
				      	
				      	
				      		System.out.println("test value : "+test);
//				      	WebElement testSingle1 = driver.findElement(By.name("_fm.pi._0.h")).findElement(By.id("RadioGroup1_0"));
//				      	testSingle1.click();
//				      	Thread.sleep(5000);
//				      	WebElement testSingle2 = driver.findElement(By.name("_fm.pi._0.h")).findElement(By.id("RadioGroup1_1"));
//				      	testSingle2.click();
//				      	Thread.sleep(5000);
//				      	WebElement testSingle3 = driver.findElement(By.name("_fm.pi._0.h")).findElement(By.id("RadioGroup1_0"));
//				      	testSingle3.click();
				      	Thread.sleep(5000);
			      }
			      else {
			    	  System.out.println("所选channel已经创建了pipeline，不能创建多条。");
			      }
			      
			      
			        
			        driver.quit();
	}
			      		
			      	
			      
			      
			      
			      
			      
			      
	

}
