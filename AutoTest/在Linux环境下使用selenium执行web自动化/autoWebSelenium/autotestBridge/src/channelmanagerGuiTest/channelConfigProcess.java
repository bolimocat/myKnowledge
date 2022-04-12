package channelmanagerGuiTest;

import java.util.ArrayList;

import org.openqa.selenium.Alert;
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
import com.autotestBridge.testcase.dom.caseCanalBean;
import com.autotestBridge.testcase.dom.caseChannelBean;
import com.autotestBridge.testcase.function.caseImplement;

public class channelConfigProcess extends resource{
	
	private String Url;
	private int ID;
	commonkit kit = new commonkit();
	checkBridgeMysql checkMysql = new checkBridgeMysql("172.16.110.33","root","root123","3306","otter");
	

	
	
	
	public channelConfigProcess(String url,int id) {
		Url = url;
		ID = id;
	}

	

	/**
	 * 新建channel
	 * @throws InterruptedException
	 */
	public void insertChannel()  throws InterruptedException {
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
	   		
	   		ArrayList link = new ArrayList();
	   		link = kit.fetchLine("./file/linkfile");
	   		dbLink db = (dbLink)link.get(0);
	   		String[] linkinfo = db.getDbline().split(",");
	   		String host = linkinfo[0];
	   		String port = linkinfo[1];
	   		String database = linkinfo[2];
	   		String user = linkinfo[3];
	   		String pass = linkinfo[4];
	   		
	   	//启动channel
		   	 //同步配置开始
			      WebElement sync_management = driver.findElement(By.linkText("同步管理"));
			      Actions action = new Actions(driver);
			      action.clickAndHold(sync_management).perform();
			      
			  //添加channel
			      WebElement insert_channel = driver.findElement(By.linkText("添加"));
			      insert_channel.click();
			      Thread.sleep(STSLEEP);
			      
			      	
			      //加载本轮用例数据
			      caseImplement imp = new caseImplement(host,user,pass,port,database);
			       ArrayList channelcase = new ArrayList();
			       channelcase = imp.channelList(ID);
			       caseChannelBean channel = (caseChannelBean)channelcase.get(0);
			       
			      String chname = channel.getChname();
			   	int sync_accord = channel.getSync_accord();
			   	int sync_mode = channel.getSync_mode();
			   	int accord_start = channel.getAccord_start();
			   	int accord_algorithm = channel.getAccord_algorithm();
			   	int requery_latency = channel.getRequery_latency();
			   	String describe_info = channel.getDescribe_info();
			      
			      //输入channel名称
			      WebElement channelName = driver.findElement(By.name("_fm.ch._0.n"));
			      channelName.sendKeys(chname);
			      Thread.sleep(STSLEEP);
			      

			      //同步一致性选择
//			      		基于数据库反查：//tr[2]/td/input
//			      		基于当前日志变更;//td/input[2]
			      	if(sync_accord==1) {
			      		WebElement syncaccord = driver.findElement(By.xpath("//tr[2]/td/input"));
			      		syncaccord.click();
			      	}
			      	if(sync_accord==2) {
			      		WebElement syncaccord = driver.findElement(By.xpath("//td/input[2]"));
			      		syncaccord.click();
			      	}
			      	
			      
			      //同步模式
//			      		行记录模式：//tr[3]/td/input
//			      		列记录模式：//tr[3]/td/input[2]
//			      String syncmodeSelect = "row";
			      	if(sync_mode==1) {
			      		WebElement syncmode = driver.findElement(By.xpath("//tr[3]/td/input"));
			      		syncmode.click();
			      	}
			      	if(sync_mode==2) {
			      		WebElement syncmode = driver.findElement(By.xpath("//tr[3]/td/input[2]"));
			      		syncmode.click();
			      	}
			      
//			      开启数据一致性：是，//tr[4]/td/input 否，//tr[3]/td/input[2]
//			      		默认单向回环补救
//			      String dataaccordSelect = "open";//open close
			      if(accord_start==1){
			    	  WebElement dataaccord = driver.findElement(By.xpath("//tr[4]/td/input"));
			    	  dataaccord.click();
			    	  Thread.sleep(STSLEEP);
			    	  
			    	  if(accord_algorithm==1) {
			    		  System.out.println("默认单向回环补救！");
				    	  	WebElement accordalgorithmSelect = driver.findElement(By.xpath("//tr[5]/td/input"));
				    	  	accordalgorithmSelect.click();
//				    	  	一致性反查数据库延迟阀值
				    	  	WebElement oppositequerylatency = driver.findElement(By.name("_fm.cha._0.r"));
				    	  	oppositequerylatency.clear();
				    	  	oppositequerylatency.sendKeys(String.valueOf(requery_latency));
			    	  }
			    	 
			    	  	
//					      描述
			    	  	WebElement describe = driver.findElement(By.name("_fm.ch._0.d"));
			    	  	describe.sendKeys(describe_info);
			    	  	
			      }
			      if(accord_start==2) {
//				      描述
		    	  	WebElement describe = driver.findElement(By.name("_fm.ch._0.d"));
		    	  	describe.sendKeys(describe_info);
			      }
			      
			      Thread.sleep(STSLEEP);
			      
			      //保存
			      WebElement save_channel = driver.findElement(By.linkText("保存"));
			      save_channel.click();

			      
			      

			      Thread.sleep(STSLEEP);
	   		driver.quit();
	}

	

	
	
	

}
