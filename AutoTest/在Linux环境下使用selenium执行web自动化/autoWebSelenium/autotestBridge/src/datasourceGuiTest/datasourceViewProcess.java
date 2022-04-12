//数据源列表
package datasourceGuiTest;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.autotestBridge.function.checkBridgeMysql;
import com.autotestBridge.function.commonkit;
import com.autotestBridge.resource.resource;

public class datasourceViewProcess extends resource {

	private String Url;
	private String datasourceName;
	checkBridgeMysql ckMysql = new checkBridgeMysql("172.16.110.33","root","root123","3306","otter");
	
	commonkit kit = new commonkit();
	
	public datasourceViewProcess(String url,String datasource) {
		Url = url;
		datasourceName = datasource;
	}
	
	//查看数据源
	public void viewDatasource() throws InterruptedException {
			//根据数据源名称获得该ID
		   int sourceId = ckMysql.datasourceId(datasourceName);
		  
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
	          
	        //数据源配置开始
	      WebElement configure_management = driver.findElement(By.linkText("配置管理"));
	      Actions action = new Actions(driver);
	      action.clickAndHold(configure_management).perform();
	      
	      WebElement datasourceconfig = driver.findElement(By.linkText("数据源配置"));
	      datasourceconfig.click();
	      
	        //根据id，打开指定的数据源地址页面进行查看。
	      driver.navigate().to(BASEURL+"/data_source_info.htm?dataMediaSourceId="+sourceId);
	        Thread.sleep(5000);
	        //截图
	        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
      	  kit.saveSnapshot("viewSource", "/home/lming/图片", srcFile);
	        driver.quit();
	}
}
