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

import com.autotestBridge.dom.dbLink;
import com.autotestBridge.function.commonkit;
import com.autotestBridge.resource.*;
import com.autotestBridge.testcase.dom.caseDataSourceBean;
import com.autotestBridge.testcase.function.caseImplement;
//import org.apache.commons.io.FileUtils;
import com.autotestBridge.testcase.function.casePickupCase;

//配置数据源
public class datasourceCongfigProcess extends resource{
	
	private String Url;
	private int ID;
	
	commonkit kit = new commonkit();
	
	public datasourceCongfigProcess(String url,int id) {
		Url = url;
		ID = id;
		
	}

	//修改数据源
	public void cfgDatasource() throws InterruptedException {
		
		commonkit kit = new commonkit();
		ArrayList link = new ArrayList();
		link = kit.fetchLine("./file/linkfile");
		dbLink db = (dbLink)link.get(0);
		String[] linkinfo = db.getDbline().split(",");
		String host = linkinfo[0];
		String port = linkinfo[1];
		String database = linkinfo[2];
		String user = linkinfo[3];
		String pass = linkinfo[4];
		
		
		//加载当前用例id对应的内容。
		caseImplement imp = new caseImplement(host,user,pass,port,database);
		ArrayList dscase = new ArrayList();
		dscase = imp.datasourceList(ID);
		
		caseDataSourceBean casedatasource = (caseDataSourceBean)dscase.get(0);
		String name = casedatasource.getName();
		String ds_type = casedatasource.getDs_type();
		String ds_user = casedatasource.getDs_user();
		String ds_pass = casedatasource.getDs_pass();
		String ds_url = casedatasource.getDs_url();
		String ds_code = casedatasource.getDs_code();
		String ds_pool = casedatasource.getDs_pool();
		
		System.out.println(" -- name -- "+name);
		System.out.println(" -- ds_type -- "+ds_type);
		System.out.println(" -- ds_user -- "+ds_user);
		System.out.println(" -- ds_pass -- "+ds_pass);
		System.out.println(" -- ds_url -- "+ds_url);
		System.out.println(" -- ds_code -- "+ds_code);
		System.out.println(" -- ds_pool -- "+ds_pool);
		
		
		
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
      
         //添加
      WebElement addSource = driver.findElement(By.linkText("添加"));
      addSource.click();
        //数据源名称
      WebElement sourceNametxt = driver.findElement(By.name("_fm.d._0.n"));
        //数据源类型
      WebElement sourceType = driver.findElement(By.name("_fm.d._0.t"));//MYSQL ORACLE DBSCALE KAFKA
        //数据源用户														_fm.d._0.t
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
      sourceNametxt.sendKeys(name);	//数据源名称	
      
      Select selectSource = new Select(sourceType);	//数据源类型
      selectSource.selectByValue(ds_type);	
      
      sourceUserName.sendKeys(ds_user);		//连接数据源用户
      sourcePassword.sendKeys(ds_pass);		//连接数据源密码
      sourceUrl.sendKeys(ds_url);	//数据源地址
      
      Select selectEncode = new Select(sourceEncode);	//数据源编码
      selectEncode.selectByValue(ds_code);
      
      	//配置数据源连接池
      poolConfig.sendKeys(ds_pool);
      
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
