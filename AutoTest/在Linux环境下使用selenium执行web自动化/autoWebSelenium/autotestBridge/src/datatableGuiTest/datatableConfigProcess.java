package datatableGuiTest;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.autotestBridge.dom.dbLink;
import com.autotestBridge.function.commonkit;
import com.autotestBridge.resource.resource;
import com.autotestBridge.testcase.dom.caseDataTableBean;
import com.autotestBridge.testcase.function.caseImplement;

public class datatableConfigProcess  extends resource{

	private String Url;
	private int ID;
	commonkit kit = new commonkit();
	
	public datatableConfigProcess(String url,int id) {
		Url = url;
		ID = id;
	}
	
	/**
	 * 添加数据表
	 * @throws InterruptedException
	 */
		public void cfgDatatable() throws InterruptedException {
			
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
			ArrayList tbcase = new ArrayList();
			tbcase = imp.datatableList(ID);
			
			caseDataTableBean casedatatable = (caseDataTableBean)tbcase.get(0);
			String dbname = casedatatable.getDb();
			String tbname = casedatatable.getTb();
			String datasource = casedatatable.getDs_name();
			
			
			
			
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
	          
	        //数据表配置开始
	      WebElement configure_management = driver.findElement(By.linkText("配置管理"));
	      Actions action = new Actions(driver);
	      action.clickAndHold(configure_management).perform();
	      
	      WebElement datatableconfig = driver.findElement(By.linkText("数据表配置"));
	      datatableconfig.click();
	      Thread.sleep(2000);
	      
	    //添加
	      WebElement addTable = driver.findElement(By.linkText("添加"));
	      addTable.click();
	      
	      //进入  添加数据表添加消息主题
	      //
	      //输入库名
	      WebElement databasetxt = driver.findElement(By.name("_fm.da._0.na"));
	      databasetxt.sendKeys(dbname);
	      //输入数据表
	      WebElement tabletxt = driver.findElement(By.name("_fm.da._0.n"));
	      tabletxt.sendKeys(tbname);
	      Thread.sleep(2000);
	      //打开选择数据源页面
	      WebElement datasourceWindow = driver.findElement(By.xpath("//input[@value='查找数据源']"));
	      datasourceWindow.click();
	      Thread.sleep(5000);

	      //选择某个数据源
	      //重新选择方式，由开发提供该文本框的可编辑状态，直接写值。
	      WebElement fetchDatasource = driver.findElement(By.xpath("(//input[@value='选择'])[2]"));
	      fetchDatasource.click();
	      Thread.sleep(2000);
	      
		}
	      
}
