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

public class pipelineViewProcess extends resource{
	private String Url;
	private int ID;
	private String channel_name;
	commonkit kit = new commonkit();
	checkBridgeMysql checkMysql = new checkBridgeMysql("172.16.110.33","root","root123","3306","otter");
	
	public pipelineViewProcess(String url,int id,String name) {
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
			      int channelId = checkMysql.channelId("source$$normal_0");
			      driver.navigate().to(BASEURL+"/add_pipeline.htm?channelId="+channelId);
			      
			      //根据pipeline的名称，进入查看页面。
			      int pipelineId = checkMysql.pipelineId(channelId);
			      	if(pipelineId==0) {
			      		System.out.println("当前channel下还没有创建pipeline。");
			      	}
			      	else {
			      		driver.navigate().to(BASEURL+"/pipeline_info.htm?pipelineId="+pipelineId);
			      		//直接截图
//			      		WebElement viewPipelineId = driver.findElement(By.xpath("//td[contains(.,'"+pipelineId+"')]"));
//			      		WebElement viewPipelineName = driver.findElement(By.xpath("//td[contains(.,'dbscale_normal')]"));
//			      	System.out.println("页面取值pipelineid = "+viewPipelineId);
//			      	System.out.println("页面取值pipelinename = "+viewPipelineName);
			      		
			      	}
			      		
			      	
			      
			      
			      
			      
			      
			      
	}

}
