package canalGuiTest;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.autotestBridge.dom.dbLink;
import com.autotestBridge.function.checkBridgeMysql;
import com.autotestBridge.function.commonkit;
import com.autotestBridge.resource.resource;
import com.autotestBridge.testcase.dom.caseCanalBean;
import com.autotestBridge.testcase.function.caseImplement;

public class canalConfigProcess extends resource{

	private String Url;
	private int ID;
	commonkit kit = new commonkit();
	checkBridgeMysql checkMysql = new checkBridgeMysql("172.16.110.33","root","root123","3306","otter");
	
	public canalConfigProcess(String url,int id) {
		Url = url;
		ID = id;
	}
	
	/**
	 * 新建cannal
	 * @throws InterruptedException
	 */
	public void insertCanal() throws InterruptedException {
		
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
		      
		    //添加
		      WebElement addCanal = driver.findElement(By.linkText("添加"));
		      addCanal.click();
		      Thread.sleep(MNSLEEP);
		      
		      //加载本轮用例数据
		      caseImplement imp = new caseImplement(host,user,pass,port,database);
		       ArrayList canalcase = new ArrayList();
		       canalcase = imp.canalList(ID);
		      
		       caseCanalBean canal = (caseCanalBean)canalcase.get(0);
		       		
		       
		       /*
	 private String db;
	 */
		String canal_name = canal.getCasename();
      int run_mode = canal.getRun_mode();
      String zkcluster = canal.getZkcluster();
      int ds_type = canal.getDs_type();
      String db_url = canal.getDb_url();
      String db_usesr = canal.getDb_usesr();
      String db_pass = canal.getDb_pass();
      String db_code = canal.getDb_code();
      int position_setup = canal.getPosition_setup();
	   int position_startup = canal.getPosition_startup();
	   String position_info = canal.getPosition_info();
		
	   
	   int tsdb_startup = canal.getTsdb_startup();
		String rdsaccesskey = canal.getRdsaccesskey();
		String rdssecretkey = canal.getRdssecretkey();
		String rdsinstanceid = canal.getRdsinstanceid();
		int store_type = canal.getStore_type();
		int batch_type = canal.getBatch_type();
		int mem_buffer_record = canal.getMem_buffer_record();
		int mem_buffer_unit = canal.getMem_buffer_unit();
		
		int HA_type = canal.getHA_type();
		String media_group_key = canal.getMedia_group_key();
		boolean heartbeat_startup = canal.isHeartbeat_startup();
		String heartbeat_sql = canal.getHeartbeat_sql();
		int heartbeat_fequence = canal.getHeartbeat_fequence();
		int heartbeat_overtime = canal.getHeartbeat_overtime();
		int heartbeat_retry = canal.getHeartbeat_retry();
		boolean heartbeat_ha = canal.isHeartbeat_ha();
		boolean other_parameters = canal.isOther_parameters();
		int meta_type = canal.getMeta_type();
		int index_type = canal.getIndex_type();
		int service_port = canal.getService_port();
		int default_overtime = canal.getDefault_overtime();
		int sendBufferSize = canal.getSendBufferSize();
		int receiveBufferSize  = canal.getReceiveBufferSize();
		int switch_backtime = canal.getSwitch_backtime();
		String filter_expression = canal.getFilter_expression();
		String describe_info = canal.getDescribe_info();
		         
		      
		       
		      
		      //输入canal名称
		      WebElement cannalname = driver.findElement(By.name("_fm.ca._0.n"));
		      cannalname.clear();
		      cannalname.sendKeys(canal_name);
		      
		      //运行模式--默认不可选
		    //zookeeper集群
		      //数据源类型
		      
		      //数据库地址
		      WebElement databaseurl = driver.findElement(By.name("_fm.can._0.g"));
		      databaseurl.clear();
		      databaseurl.sendKeys(db_url);
		      
		      //帐号
		      WebElement databaseaccount = driver.findElement(By.name("_fm.can._0.d"));
		      databaseaccount.clear();
		      databaseaccount.sendKeys(db_usesr);
		      
		      //密码
		      WebElement databasepass = driver.findElement(By.name("_fm.can._0.db"));
		      databasepass.clear();
		      databasepass.sendKeys(db_pass);
		      
		      //连接编码
		      WebElement databasecode = driver.findElement(By.name("_fm.can._0.co"));
		      databasecode.clear();
		      databasecode.sendKeys(db_code);
		      
		      //位点自定义选择
//		      String checkUsePosition = "1";
		      WebElement useposition = driver.findElement(By.id("positionSuperConfig"));
		      if(position_setup==1) {
		    	  useposition.click();
		    	  //是否启用gtid位点
		    	  WebElement isuse_gtid = driver.findElement(By.id("RadioGroup8_0_0"));
		    	  WebElement nouse_gtid = driver.findElement(By.id("RadioGroup8_1_0"));
		    	  	if(position_startup == 0) {
		    	  		nouse_gtid.click();
		    	  	}
		    	  	if(position_startup == 1) {
		    	  		isuse_gtid.click();
		    	  	}
		    	  //输入位点信息
		    	  WebElement positioninfo = driver.findElement(By.name("_fm.can._0.p"));
		    	  positioninfo.clear();
		    	  positioninfo.sendKeys(position_info);
		      }
		      
		      //是否开启表结构TSDB
		      WebElement istsdb = driver.findElement(By.id("RadioGroup8_0_0"));
		      WebElement notsdb = driver.findElement(By.id("RadioGroup8_1_0"));
		      if(tsdb_startup == 0) {
		    	  //不开启
		    	  notsdb.click();
		      }
		      if(tsdb_startup == 1) {
		    	  //开启
		    	  istsdb.click();
		      }
		      
		      
		      //rds accesskey： 	
		      WebElement accesskey = driver.findElement(By.name("_fm.can._0.rd"));
		      accesskey.clear();
		      accesskey.sendKeys(rdsaccesskey);
		      
		      //secretkey
		      WebElement secretkey = driver.findElement(By.name("_fm.can._0.rds"));
		      secretkey.clear();
		      secretkey.sendKeys(rdssecretkey);
		      
		      //instanceId
		      WebElement instanceId = driver.findElement(By.name("_fm.can._0.rdsi"));
		      instanceId.clear();
		      instanceId.sendKeys(rdsinstanceid);
		      
		      
		      	//存储机制 int store_type = canal.getStore_type();
		      //内存存储batch获取模式
		      WebElement batchmode_MEMSIZE = driver.findElement(By.id("RadioGroup6_0"));
		      WebElement batchmode_ITEMSIZE  = driver.findElement(By.id("RadioGroup6_1"));
		      if(batch_type == 0) {
					batchmode_MEMSIZE.click();
				}
				if(batch_type == 1) {
					batchmode_ITEMSIZE.click();
				}
			 //内存存储buffer记录数
		      WebElement membuffer = driver.findElement(By.name("_fm.can._0.me"));
		      membuffer.clear();
		      membuffer.sendKeys(String.valueOf(mem_buffer_record));
		      
		      //内存存储buffer记录单元大小
		      WebElement membufferunit = driver.findElement(By.name("_fm.can._0.mem"));
		      membufferunit.clear();
		      membufferunit.sendKeys(String.valueOf(mem_buffer_unit));
		     
		      //HA机制
				WebElement ha_heartbeat = driver.findElement(By.id("RadioGroup6_0"));
				WebElement ha_media = driver.findElement(By.id("RadioGroup6_2"));
				if(HA_type == 0) {
					//heartbeat机制
					ha_heartbeat.click();
				}
				
				
		      if(HA_type == 1) {//选择media
		    	  //media group key:
		    	  System.out.println("media group key");
		    	 
		    	//向下滚动滚轮
		    	 	String setscroll = "document.documentElement.scrollTop=" + 1500;
					JavascriptExecutor jse=(JavascriptExecutor) driver;
					jse.executeScript(setscroll);
		    	  
		    	  Thread.sleep(MNSLEEP);
		    	  WebElement mediagroupkey = driver.findElement(By.name("_fm.can._0.med"));
		    	  mediagroupkey.clear();
		    	  mediagroupkey.sendKeys(media_group_key);
		      }
		      
		     
				
		      
		      //是否开启心跳
		      if(heartbeat_startup) {//开启心跳
		    	  WebElement hbisstart = driver.findElement(By.id("RadioGroup8_0"));
		    	  hbisstart.click();
		    	  
		    	  //心跳sql
		    	  WebElement heartsql = driver.findElement(By.name("_fm.can._0.dete"));
		    	  heartsql.clear();
		    	  heartsql.sendKeys(heartbeat_sql);
		    	  
		    	  //心跳检测频率
		    	  WebElement heartfrequency = driver.findElement(By.name("_fm.can._0.detec"));
		    	  heartfrequency.clear();
		    	  heartfrequency.sendKeys(String.valueOf(heartbeat_fequence));
		    	  
		    	  //心跳超时时间
		    	  WebElement heartovertime = driver.findElement(By.name("_fm.can._0.detect"));
		    	  heartovertime.clear();
		    	  heartovertime.sendKeys(String.valueOf(heartbeat_overtime));
		    	  
		    	  //心跳检查重试次数
		    	  WebElement heartrecheck = driver.findElement(By.name("_fm.can._0.detecti"));
		    	  heartrecheck.clear();
		    	  heartrecheck.sendKeys(String.valueOf(heartbeat_retry));
		    	  
		    	  //是否启用心跳HA
		    	  WebElement ishaHeartbeat = driver.findElement(By.id("RadioGroup8_0"));
		    	  WebElement nohaHeartbeat = driver.findElement(By.id("RadioGroup8_1"));
		    	 if(heartbeat_ha) {
		    		  //启用心跳HA
		    		 ishaHeartbeat.click();
		       	  }
		    	 else {
		    		 nohaHeartbeat.click();
		    	 }
		      }
		      else {
		    	  System.out.println("不开启心跳");
		    	  WebElement hbnostart = driver.findElement(By.id("RadioGroup8_1"));
		    	  hbnostart.click();
		      }
		      
		      
		      //其他设置参数
		     
		      if(other_parameters) {
		    	  System.out.println("设置 其他 参数生效");
		    	  WebElement otherpara = driver.findElement(By.id("networkSuperConfig"));
		    	  otherpara.click();
		    	  
		    	  
		    	  //meta机制 默认
		    	  WebElement meta = driver.findElement(By.name("_fm.can._0.m"));
		    	  meta.click();
		    	 
		    	  //索引机制 默认
		    	  WebElement index = driver.findElement(By.name("_fm.can._0.in"));
		    	  index.click();
		    	  
		    	  //服务端口：
		    	  WebElement serviceport = driver.findElement(By.name("_fm.can._0.po"));
		    	  serviceport.clear();
		    	  serviceport.sendKeys(String.valueOf(service_port));
		    	  
		    	  //默认连接超时
		    	  WebElement defaultovertime = driver.findElement(By.name("_fm.can._0.de"));
		    	  defaultovertime.clear();
		    	  defaultovertime.sendKeys(String.valueOf(default_overtime));
		    	  
		    	  //sendBufferSize
		    	  WebElement sendbufferSize = driver.findElement(By.name("_fm.can._0.re"));
		    	  sendbufferSize.clear();
		    	  sendbufferSize.sendKeys(String.valueOf(sendBufferSize));
		    	  
		    	  //receiveBufferSize
		    	  WebElement receivebufferSize = driver.findElement(By.name("_fm.can._0.se"));
		    	  receivebufferSize.clear();
		    	  receivebufferSize.sendKeys(String.valueOf(receiveBufferSize));
		    	  
		    	  //切换回退时间
		    	  WebElement backtime = driver.findElement(By.name("_fm.can._0.fa"));
		    	  backtime.clear();
		    	  backtime.sendKeys(String.valueOf(switch_backtime));
		      }
		      else {
		    	  System.out.println("设置 其他 参数 不 生效");
		      }
		      

		      //输入过滤表达式
		     WebElement filterexpression = driver.findElement(By.name("_fm.can._0.b"));
		     filterexpression.clear();
		     filterexpression.sendKeys(filter_expression);
		     
		      //描述信息
		     WebElement describeinfo = driver.findElement(By.name("_fm.ca._0.d"));
		     describeinfo.clear();
		     describeinfo.sendKeys(describe_info);
		      
		      Thread.sleep(STSLEEP);
//		      driver.quit();
		      
		      
		      WebElement save = driver.findElement(By.linkText("保存"));
		      save.click();
		      
		      
	}

	//查看现有的cannal信息
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
		      int canalId = checkMysql.canalId("dbscale_normal_canal");
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
