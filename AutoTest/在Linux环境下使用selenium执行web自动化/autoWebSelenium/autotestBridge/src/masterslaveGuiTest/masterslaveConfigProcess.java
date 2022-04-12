package masterslaveGuiTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.autotestBridge.function.checkBridgeMysql;
import com.autotestBridge.resource.resource;

public class masterslaveConfigProcess extends resource{

	private String Url;
	private String datasourceName;
	checkBridgeMysql ckMysql = new checkBridgeMysql("172.16.110.33","root","root123","3306","otter");
	
	public masterslaveConfigProcess(String url,String sourcename) {
		Url = url;
		datasourceName = sourcename;
	}
	
	//添加一个新的主备信息
	public void insertMasterSlave() throws InterruptedException {
		//获取master数据源的id
		int masterid = ckMysql.datasourceId(datasourceName);
		
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
	         
	   		Thread.sleep(MNSLEEP);
	   	 //登录结束
	          
	        //主备配置开始
	      WebElement configure_management = driver.findElement(By.linkText("配置管理"));
	      Actions action = new Actions(driver);
	      action.clickAndHold(configure_management).perform();
	      
	      WebElement masterslaveconfig = driver.findElement(By.linkText("主备配置"));
	      masterslaveconfig.click();
	      Thread.sleep(STSLEEP);
	      	
	      //进入主备信息添加页面
	      WebElement insert = driver.findElement(By.linkText("添加"));
	      insert.click();
	      Thread.sleep(STSLEEP);
	      
	      //输入source
	      WebElement inputsource = driver.findElement(By.name("_fm.datam._0.g"));//group key
	      inputsource.clear();
	      inputsource.sendKeys("source$$test1");
	      
	      //输入master
	      WebElement inputmaster = driver.findElement(By.name("_fm.datam._0.m"));
	      inputmaster.clear();
	      inputmaster.sendKeys(String.valueOf(masterid));
	      
	      //保存
	      WebElement save = driver.findElement(By.linkText("保存"));
	      save.click();
	      
	      
	      
	      driver.quit();
	      
//	      driver.quit();
	}
}
