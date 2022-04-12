//数据源配置业务流程
package datasourceGuiTest;
import java.io.File;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.autotestBridge.dom.datasourceBean;
import com.autotestBridge.function.checkBridgeMysql;
import com.autotestBridge.function.commonkit;
import com.autotestBridge.resource.*;
//import org.apache.commons.io.FileUtils;

public class datasourceUpdateProcess extends resource{
	
	private String Url;
	private String datasourceName;
	commonkit kit = new commonkit();
	checkBridgeMysql ckMysql = new checkBridgeMysql("172.16.110.33","root","root123","3306","otter");
	
	public datasourceUpdateProcess(String url,String datasource) {
		Url = url;
		datasourceName = datasource;
	}

	//配置数据源
	public void cfgDatasource() throws InterruptedException {
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
   		
   		//获取当前id的完整信息
          ArrayList datasourceInfo = new ArrayList();
          datasourceInfo = ckMysql.datasourceInfo(sourceId);
          	for(int i=0;i<datasourceInfo.size();i++) {
          		datasourceBean source = (datasourceBean)datasourceInfo.get(i);
          		System.out.println(source.getNAME()+"  -  "+source.getTYPE()+"    -    "+source.getPROPERTIES());
          	}
          
   		
   		
   		
   		
   		
   		
   		
   		
   		
   		
   		
   		
   		
   		
   		
   		//带修改内容：
   		
   		//数据源配置开始
      WebElement configure_management = driver.findElement(By.linkText("配置管理"));
      Actions action = new Actions(driver);
      action.clickAndHold(configure_management).perform();
      
      WebElement datasourceconfig = driver.findElement(By.linkText("数据源配置"));
      datasourceconfig.click();
      
         //打开要修改的数据源信息
      driver.navigate().to(BASEURL+"/edit_data_source.htm?dataMediaSourceId=11");
      
      
      
      
        //数据源名称
      WebElement sourceNametxt = driver.findElement(By.name("_fm.d._0.n"));
        //数据源类型
      WebElement sourceType = driver.findElement(By.name("_fm.d._0.t"));//MYSQL ORACLE DBSCALE KAFKA
        //数据源用户
      WebElement sourceUserName = driver.findElement(By.name("_fm.d._0.u"));
       //数据源密码
      WebElement sourcePassword = driver.findElement(By.name("_fm.d._0.p"));
      //数据源url
      WebElement sourceUrl = driver.findElement(By.name("_fm.d._0.ur"));
      //数据源编码
      WebElement sourceEncode = driver.findElement(By.name("_fm.d._0.e")); //GBK UTF8 UTF8MB4 ISO-8859-1
      //连接池配置
      WebElement poolConfig = driver.findElement(By.name("_fm.d._0.po"));
      //数据源验证
      
      //保存数据源
      WebElement sourcesave = driver.findElement(By.linkText("保存"));
      
        //输入内容
      sourceNametxt.sendKeys("ccc");	//数据源名称	
      
      Select selectSource = new Select(sourceType);	//数据源类型
      selectSource.selectByValue("MYSQL");	
      
      sourceUserName.sendKeys("user");		//连接数据源用户
      sourcePassword.sendKeys("pass");		//连接数据源密码
      sourceUrl.sendKeys("jdbc；。。。。");	//数据源地址
      
      Select selectEncode = new Select(sourceEncode);	//数据源编码
      selectEncode.selectByValue("UTF8");
      
      	//配置数据源连接池
      poolConfig.sendKeys("{\"initialSize\":1,\"maxActive\":5,\"maxWait\":60000,\"minIdle\":0,\"maxIdle\":1,\"removeAbandonedTimeout\":60,\"numTestsPerEvictionRun\":-1,\"timeBetweenEvictionRunsMillis\":60000,\"minEvictableIdleTimeMillis\":1800000,\"testOnBorrow\":false,\"testOnReturn\":false,\"testWhileIdle\":true,\"useServerPrepStmts\":false,\"rewriteBatchedStatements\":true,\"zeroDateTimeBehavior\":\"convertToNull\",\"yearIsDateType\":false,\"noDatetimeStringSync\":true,\"jdbcCompliantTruncation\":false}");
      
      //验证数据源有效性
      driver.findElement(By.cssSelector("th > input")).click();
      Thread.sleep(1000);
      WebElement sourceCheckResult = driver.findElement(By.id("result"));
//      	判断数据源有效性
      	if(sourceCheckResult.getText().equals("抱歉,数据库未通过验证,请检查相关配置!")) {
      		System.out.println("数据源验证失败！");
      			//截图保存
      		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
      		kit.saveSnapshot("myPicture", ERRPICPATH, srcFile);

      			//数据源无效，退出且截图。
//      		 driver.quit();
      	}
      
      Thread.sleep(2000);
      sourcesave.click();
        //数据源配置结束  数据源配置
      
      
      driver.quit();
	}

}
