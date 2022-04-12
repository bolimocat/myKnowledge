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

public class pipelineConfigProcess extends resource{
	private String Url;
	private int ID;
	private String channel_name;
	commonkit kit = new commonkit();
	checkBridgeMysql checkMysql = new checkBridgeMysql("172.16.110.33","root","root123","3306","otter");
	
	public pipelineConfigProcess(String url,int id,String name) {
		Url = url;
		ID = id;
		channel_name = name;
	}
	
	/**
	 * 新建pipeline
	 * @throws InterruptedException
	 */
	public void insertPipeline() throws InterruptedException {
		WebDriver driver = new FirefoxDriver(); 
		driver.get(Url);
		driver.manage().window().maximize();
		
		ArrayList link = new ArrayList();
   		link = kit.fetchLine("./file/linkfile");
   		dbLink db = (dbLink)link.get(0);
   		String[] linkinfo = db.getDbline().split(",");
   		String host = linkinfo[0];
   		String port = linkinfo[1];
   		String database = linkinfo[2];
   		String user = linkinfo[3];
   		String pass = linkinfo[4];
		   
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
			      
			    //加载本轮用例数据
			      caseImplement imp = new caseImplement(host,user,pass,port,database);
			       ArrayList pipelinecase = new ArrayList();
			       pipelinecase = imp.pipelineList(ID);
			       casePipelineBean pip = (casePipelineBean)pipelinecase.get(0);
			       
			      String chname = pip.getChname();
			   	String pipelinename = pip.getPipelinename();
			   	boolean select_node = pip.isSelect_node();
			   	boolean load_node = pip.isLoad_node();
			   	int parallelism = pip.getParallelism();
			   	int oppositethread = pip.getOppositethread();
			   	int loaddatathread = pip.getLoaddatathread();
			   	int loadfilethread = pip.getLoadfilethread();
			   	int isClicked = pip.getIsClicked();
			   	int isMainWeb = pip.getIsMainWeb();
			   	String canalName = pip.getCanalName();
			   	int consumption = pip.getConsumption();
			   	int overtime = pip.getOvertime();
			   	String describe_info = pip.getDescribe_info();
			   	boolean issuper = pip.isIssuper();
			   	int isBatch = pip.getIsBatch();
			   	int batchsize = pip.getBatchsize();
			   	int ignoreSelect = pip.getIgnoreSelect();
			   	int ignoreLoad = pip.getIgnoreLoad();
			   	String arbiter = pip.getArbiter();
			   	String loadbalance = pip.getLoadbalance();
			   	String transport = pip.getTransport();
			   	
			   	int selectlog = pip.getSelectlog();
			   	int selectlogdetail = pip.getSelectlogdetail();
			   	int loadlog = pip.getLoadlog();
			   	int dryrunmode = pip.getDryrunmode();
			   	int supportddl = pip.getSupportddl();
			   	boolean ddlView = pip.isDdlView();
			   	boolean ddlFunction = pip.isDdlFunction();
			   	boolean ddlProcedure = pip.isDdlProcedure();
			   	boolean ddlEvent = pip.isDdlEvent();
			   	boolean ddlTriger = pip.isDdlTriger();
			   	
			   	boolean jumbddlwrong = pip.isJumbddlwrong();
			   	boolean fileduplicate = pip.isFileduplicate();
			   	boolean transportencry = pip.isTransportencry();
			   	boolean publicnet = pip.isPublicnet();
			   	boolean jumpfreegate = pip.isJumpfreegate();
			   	boolean jumpreversequery = pip.isJumpreversequery();
			   	boolean tbtypetrans = pip.isTbtypetrans();
			   	boolean compatiblesync = pip.isCompatiblesync();
			   	String customsyncsign = pip.getCustomsyncsign();
			   	
			      
			      
			 //根据channel名称，进入pipeline列表页
			      int channelId = checkMysql.channelId(channel_name);
//			      driver.navigate().to(BASEURL+"/pipeline_list.htm?channelId="+channelId);
//			      Thread.sleep(5000);
			  
			  //添加新pipeline
			       //根据channel的id判断是否可以创建pipeline
			      				      
			      if(checkMysql.havePip(channelId)) {
				      driver.navigate().to(BASEURL+"/add_pipeline.htm?channelId="+channelId);
				      Thread.sleep(5000);
				      	//输入pipeline名字
				      WebElement pipelineName = driver.findElement(By.name("_fm.p._0.n"));
				      pipelineName.sendKeys(pipelinename);
				      //select机器
				      WebElement selectMachine = driver.findElement(By.name("_fm.p._0.s"));
				      Select selectmachine = new Select(selectMachine);
//				      boolean isMultSelect = true;	//判断选择单个机器节点还是多个机器节点
				      if(select_node) {
				    	  selectmachine.selectByValue("1");
					     selectmachine.selectByValue("2");
				      }
				      else {
				    	  selectmachine.selectByValue("2");
				      }
				      //load机器
				      WebElement loadMachine = driver.findElement(By.name("_fm.p._0.l"));
				      Select loadmachine = new Select(loadMachine);
				    
				      if(load_node) {
				    	  loadmachine.selectByValue("1");
				    	  loadmachine.selectByValue("2");
				      }
				      else {
				    	  loadmachine.selectByValue("1");
				      }
				      				      
				      //输入并行度
				      WebElement paralism = driver.findElement(By.name("_fm.pi._0.pa"));
				      paralism.clear();
				      paralism.sendKeys(String.valueOf(parallelism));
				      
				      //输入反查线程数
				      WebElement oppothread = driver.findElement(By.name("_fm.pi._0.e"));
				      oppothread.clear();
				      oppothread.sendKeys(String.valueOf(oppositethread));
				      
				    //输入数据载入线程数
				      WebElement loadthread = driver.findElement(By.name("_fm.pi._0.l"));
				      loadthread.clear();
				      loadthread.sendKeys(String.valueOf(loaddatathread));
				      
				    //输入文件载入线程数
				      WebElement loadfthread = driver.findElement(By.name("_fm.pi._0.f"));
				      loadfthread.clear();
				      loadfthread.sendKeys(String.valueOf(loadfilethread));
				      
     			   //选择是否主站点
//				      isClicked = "0";//0是，1不是
				      if(isClicked==0) {
				    	  //是
				    	  WebElement iMainWeb = driver.findElement(By.id("RadioGroup1_"+isClicked));
					      iMainWeb.click();
				      }
				      if(isClicked==1) {
				    	  //不是
				    	  WebElement nMainWeb = driver.findElement(By.id("RadioGroup1_"+isClicked));
				    	  nMainWeb.click();
				      }
				     
				      
				    //输入cannal名称[暂时不考虑新开窗口选择]
				      WebElement canName = driver.findElement(By.name("_fm.pi._0.de"));
				      canName.sendKeys(canalName);
				      
				      
				     //输入消费批次
				      WebElement consumpe = driver.findElement(By.name("_fm.pi._0.m"));
				      consumpe.clear();
				      consumpe.sendKeys(String.valueOf(consumption));
				      
				    //输入批次超时时间
				      WebElement over = driver.findElement(By.name("_fm.pi._0.ba"));
				      over.clear();
				      over.sendKeys(String.valueOf(overtime));//格式: -1不进行控制，0代表永久，>0则按照指定时间控制
				      
				      //输入描述
				      WebElement description = driver.findElement(By.name("_fm.p._0.d"));
				      description.clear();
				      description.sendKeys(describe_info);
				      
				      
				      //判断是否选择高级
//				      boolean issuper = true;
				      if(issuper) {
				    	  WebElement superbox = driver.findElement(By.id("superConfig"));  
				    	  superbox.click();
				    	  //向下滚动滚轮
				    	 	String setscroll = "document.documentElement.scrollTop=" + 1500;
							JavascriptExecutor jse=(JavascriptExecutor) driver;
							jse.executeScript(setscroll);
				    	  

							//是否使用batch,0使用，1不使用 [id异常]
				    	  if(isBatch==0) {
				    		  WebElement batch = driver.findElement(By.id("RadioGroup2_"+isBatch)); 
					    	  batch.click();
				    	  }
				    	  if(isBatch==1) {
				    		  WebElement batch = driver.findElement(By.id("RadioGroup2_"+isBatch)); 
					    	  batch.click();
				    	  }
				    	  
				    	  
				    	  //输入批处理大小
				    	  WebElement btsize = driver.findElement(By.name("_fm.pi._0.b"));
				    	  btsize.clear();
				    	  btsize.sendKeys(String.valueOf(batchsize));
				    	  
				    	  //是否跳过Select异常 0是 1否
				    	  if(ignoreSelect==0) {
				    		  WebElement ignoreselect = driver.findElement(By.id("RadioGroup2_"+ignoreSelect));
					    	  ignoreselect.click();
				    	  }
				    	  if(ignoreSelect==1) {
				    		  WebElement ignoreselect = driver.findElement(By.id("RadioGroup2_"+ignoreSelect));
					    	  ignoreselect.click();
				    	  }
				    	  
				    	  //是否跳过Load异常
//				    	  String ignoreLoad = "0";
				    	  if(ignoreLoad==0) {
				    		  WebElement ignoreload = driver.findElement(By.id("RadioGroup3_"+ignoreLoad));
					    	  ignoreload.click();
				    	  }
				    	  if(ignoreLoad==1) {
				    		  WebElement ignoreload = driver.findElement(By.id("RadioGroup3_"+ignoreLoad));
					    	  ignoreload.click();
				    	  }
				    	  
				    	  //仲裁器调度模式： 	
				    	  WebElement arbitermod = driver.findElement(By.name("_fm.pi._0.a"));	
				    	  Select selectArbiter = new Select(arbitermod);	//数据源类型
				    	  selectArbiter.selectByValue(arbiter);//AUTOMATIC,RPC,ZOOKEEPER,MEMORY
				    	  
				    	  //负载均衡
				    	  WebElement balance = driver.findElement(By.name("_fm.pi._0.lb"));	
				    	  Select selectLoadbalance = new Select(balance);	//数据源类型
				    	  selectLoadbalance.selectByValue(loadbalance);//Stick,RoundRbin,Random
				    	  
				    	  //传输模式
				    	  WebElement transportmod = driver.findElement(By.name("_fm.pi._0.pi"));	
				    	  Select selectTransport = new Select(transportmod);	//数据源类型
				    	  selectTransport.selectByValue(transport);//AUTOMATIC,RPC,HTTP
				    	  				    	 		
						   	
						  				    	  
				    	  //select日志 [id有异常]
						   if(selectlog==0) {
							   WebElement selector = driver.findElement(By.id("RadioGroup1_"+selectlog));
							   selector.click();
						   }
						   if(selectlog==1) {
							   WebElement selector = driver.findElement(By.id("RadioGroup1_"+selectlog));
							   selector.click();
						   }
						   	
						   							   	
				    	  //select详细日志
						   if(selectlogdetail==0) {
							   WebElement selectordetail = driver.findElement(By.id("RadioGroup1_"+selectlogdetail));
							   selectordetail.click();
						   }
						   if(selectlogdetail==1) {
							   WebElement selectordetail = driver.findElement(By.id("RadioGroup1_"+selectlogdetail));
							   selectordetail.click();
						   }
						   
						   
				    	  //记录load日志
						   if(loadlog==0) {
							   WebElement loadlogmod = driver.findElement(By.id("RadioGroup1_"+loadlog));
							   loadlogmod.click();
						   }
						   if(loadlog==1) {
							   WebElement loadlogmod = driver.findElement(By.id("RadioGroup1_"+loadlog));
							   loadlogmod.click();
						   }	
						   
				    	  //dryRun模式
						   if(dryrunmode==0) {
							   WebElement dryRun = driver.findElement(By.id("RadioGroup1_"+dryrunmode));
							   dryRun.click();
						   }
						   if(dryrunmode==1) {
							   WebElement dryRun = driver.findElement(By.id("RadioGroup1_"+dryrunmode));
							   dryRun.click();
						   }
						   
				    	  //支持ddl同步
						   if(supportddl==0) {
							   WebElement sptddl = driver.findElement(By.id("RadioGroup1_"+supportddl));
						   }
						   if(supportddl==1) {
							   WebElement sptddl = driver.findElement(By.id("RadioGroup1_"+supportddl));
						   }
						   
						   	
						   	
				    	  //ddl同步视图
//				    	  boolean ddlView = true;//true or false
				    	  WebElement ddlViewSelect = driver.findElement(By.name("_fm.pi._0.ddl"));
				    	  if(ddlView) {
				    		  ddlViewSelect.click();
				    	  }
				    	  
				    	  //ddl同步函数
				    	
				    	  WebElement ddlFunctionSelect = driver.findElement(By.name("_fm.pi._0.ddlf"));
				    	  if(ddlFunction) {
				    		  ddlFunctionSelect.click();
				    	  }
				    	  
				    	  //ddl同步过程
				    	  WebElement ddlProcedureSelect = driver.findElement(By.name("_fm.pi._0.ddlp"));
				    	  if(ddlProcedure) {
				    		  ddlProcedureSelect.click();
				    	  }
				    	  
				    	  //ddl同步事件
				    	  WebElement ddlEventSelect = driver.findElement(By.name("_fm.pi._0.ddle"));
				    	  if(ddlEvent) {
				    		  ddlEventSelect.click();
				    	  }
				    	  
				    	  //ddl同步触发器
				    	  WebElement ddlTrigerSelect = driver.findElement(By.name("_fm.pi._0.ddlt"));
				    	  if(ddlTriger) {
				    		  ddlTrigerSelect.click();
				    	  }
				    	  
						   	//跳过ddl异常
				    	  if(jumbddlwrong) {
				    		  WebElement jumpwrong = driver.findElement(By.id("RadioGroup2_0"));
				    		  jumpwrong.click();
				    	  }
				    	  else {
				    		  WebElement jumpwrong = driver.findElement(By.id("RadioGroup2_1"));
				    		  jumpwrong.click();
				    	  }
				    	  
				    	  
				    	  //文件重复同步对比
				    	  if(fileduplicate) {
				    		  WebElement fduplicate = driver.findElement(By.id("RadioGroup2_0"));
				    		  fduplicate.click();
				    	  }
				    	  else {
				    		  WebElement fduplicate = driver.findElement(By.id("RadioGroup2_1"));
				    		  fduplicate.click();
				    	  }
				    	  
				    	  
				    	  //文件传输加密
				    	  if(transportencry) {
				    		  WebElement transencry = driver.findElement(By.id("RadioGroup2_0"));
				    		  transencry.click();
				    	  }
				    	  else {
				    		  WebElement transencry = driver.findElement(By.id("RadioGroup2_1"));
				    		  transencry.click();
				    	  }
				    	  
				    	  //启用公网同步
				    	  if(publicnet) {
				    		  WebElement publicsame = driver.findElement(By.id("RadioGroup2_0"));
				    		  publicsame.click();
				    	  }
				    	  else {
				    		  WebElement publicsame = driver.findElement(By.id("RadioGroup2_1"));
				    		  publicsame.click();
				    	  }
				    	  	
						   	
				    	  //跳过自由门数据
						   	if(jumpfreegate) {
					    		  WebElement freegate = driver.findElement(By.id("RadioGroup2_0"));
					    		  freegate.click();
					    	  }
					    	  else {
					    		  WebElement freegate = driver.findElement(By.id("RadioGroup2_1"));
					    		  freegate.click();
					    	  }
						   	
						   	
				    	  //跳过反查无记录数据
						   	if(jumpreversequery) {
					    		  WebElement jumprever = driver.findElement(By.id("RadioGroup2_0"));
					    		  jumprever.click();
					    	  }
					    	  else {
					    		  WebElement jumprever = driver.findElement(By.id("RadioGroup2_1"));
					    		  jumprever.click();
					    	  }
						   	
						   	
						   	
				    	  //启用数据表类型转化
							if(tbtypetrans) {
					    		  WebElement typetrans = driver.findElement(By.id("RadioGroup2_0"));
					    		  typetrans.click();
					    	  }
					    	  else {
					    		  WebElement typetrans = driver.findElement(By.id("RadioGroup2_1"));
					    		  typetrans.click();
					    	  }
						   	
				    	  //兼容字段新增同步
							if(compatiblesync) {
					    		  WebElement compatsync = driver.findElement(By.id("RadioGroup2_0"));
					    		  compatsync.click();
					    	  }
					    	  else {
					    		  WebElement compatsync = driver.findElement(By.id("RadioGroup2_1"));
					    		  compatsync.click();
					    	  }
				    	  
				    	//自定义同步标记
				    	  WebElement costomsync = driver.findElement(By.name("_fm.pi._0.c"));
				    	  costomsync.clear();
				    	  costomsync.sendKeys(customsyncsign);
				    	  Thread.sleep(5000);
				      }
				      else
				      {
				    	  System.out.println("不设置高级选项");
				    	  Thread.sleep(5000);
				      }
				      
				      WebElement save = driver.findElement(By.linkText("保存"));
				      save.click();
				      
				      Thread.sleep(5000);
			      }
			      else {
			    	  System.out.println("所选channel已经创建了pipeline，不能创建多条。");
			      }
			      
			      
			        
			        driver.quit();
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
