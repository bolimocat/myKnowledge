package mappingmanagerGuiTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.autotestBridge.function.checkBridgeMysql;
import com.autotestBridge.resource.resource;

public class mappingConfigProcess extends resource{

	private String Url;
	private String channelName;
	checkBridgeMysql checkMysql = new checkBridgeMysql("172.16.110.33","root","root123","3306","otter");
	
	public mappingConfigProcess(String url,String channelname) {
		Url = url;
		channelName = channelname;
	}
	
	//在一个pipeline下插入一个映射关系
	public void insertMapping()  throws InterruptedException {
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
	   		
	   	//进入制定channel
		   	 
			      WebElement sync_management = driver.findElement(By.linkText("同步管理"));
			      Actions action = new Actions(driver);
			      action.clickAndHold(sync_management).perform();
			    
			      //根据channel名称，进入pipeline列表页
			      int channelId = checkMysql.channelId(channelName);
			    //根据channel的id判断是否可以创建pipeline
			      if(checkMysql.havePip(channelId)) {
			    	  System.out.println("当前channel下还没有pipeline。");
			      }
			      else {
			    	  //直接进入当前pipeline的mapping页
			    	  int pipelineId = checkMysql.pipelineId(channelId);
			    	  driver.navigate().to(BASEURL+"/dataMediaPairList.htm?pipelineId="+pipelineId);
			    	  
			    	  //添加一条
			    	  WebElement insert = driver.findElement(By.linkText("添加"));
			    	  insert.click();
			    	  Thread.sleep(STSLEEP);
			    	  
			    	  //选择源数据表
			    	  //选择目标数据表
			    	  
			    	  //输入权重
			    	  WebElement weight = driver.findElement(By.name("_fm.dat._0.pu"));
			    	  weight.clear();
			    	  weight.sendKeys("5");
			    	  
			    	  //操作类型
			    	  boolean isInsert = false;
			    	  boolean isUpdate = false;
			    	  boolean isDelete = false;
			    	  if(isInsert) {
			    		  WebElement chxInsert = driver.findElement(By.name("_fm.data._0.d"));
			    		  chxInsert.click();
			    	  }
			    	  if(isUpdate) {
			    		  WebElement chxUpdate = driver.findElement(By.name("_fm.data._0.dm"));
			    		  chxUpdate.click();
			    	  }
			    	  if(isDelete) {
			    		  WebElement chxDelete = driver.findElement(By.name("_fm.data._0.dml"));
			    		  chxDelete.click();
			    	  }
			    	  
			    	  //视图模式id
			    	  
			    	  //EventProcessor类型:
			    	  WebElement EventProcessor = driver.findElement(By.name("_fm.dat._0.fi"));
			    	  Select processor = new Select(EventProcessor);	
			    	  processor.selectByValue("CLAZZ");	//CLAZZ SOURCE
			    	  
			    	 //EventProcessor文本
			    	  WebElement EventProcessorText = driver.findElement(By.name("_fm.dat._0.fil"));
			    	  EventProcessorText.clear();
			    	  EventProcessorText.click();
			    	  
			    	  //FileResolver类型
			    	  WebElement FileResolver = driver.findElement(By.name("_fm.dat._0.r"));
			    	  Select resolver = new Select(FileResolver);	
			    	  resolver.selectByValue("CLAZZ");	//CLAZZ SOURCE
			    	  
			    	  //FileResolver文本: 	
			    	  WebElement FileResolverText = driver.findElement(By.name("_fm.dat._0.re"));
			    	  FileResolverText.clear();
			    	  FileResolverText.click();
			    	  
			    	  //判断是否立刻保存
			    	  if(true) {
			    		  WebElement save = driver.findElement(By.linkText("保存"));
			    		  save.click();
			    	  }
			    	  else {
			    		  WebElement next = driver.findElement(By.linkText("下一步"));
			    		  next.click();
			    	  }
			    	  
			    	  
			      }
			      
			      
	}
	
}
