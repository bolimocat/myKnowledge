package com.autotestBridge.main;

import java.util.ArrayList;

import com.autotestBridge.dom.dbLink;
import com.autotestBridge.function.commonkit;
import com.autotestBridge.resource.resource;
import com.autotestBridge.testcase.dom.caseTestCaseBean;
import com.autotestBridge.testcase.function.caseImplement;
import com.autotestBridge.testcase.function.casePickupCase;
import com.autotestBridge.testcase.function.extractCase;
import com.autotestBridge.testcase.function.testRun;
import com.autotestBridge.testcase.function.testScene;

import canalGuiTest.canalConfigProcess;
import canalGuiTest.canalViewProcess;
import channelmanagerGuiTest.channelConfigProcess;
import channelmanagerGuiTest.channelStartupProcess;
import datasourceGuiTest.datasourceCongfigProcess;
import datasourceGuiTest.datasourceViewProcess;
import datatableGuiTest.datatableConfigProcess;
import mappingmanagerGuiTest.mappingConfigProcess;
import masterslaveGuiTest.masterslaveConfigProcess;
import pipelinemanagerGuiTest.pipelineConfigProcess;
import pipelinemanagerGuiTest.pipelineTestProcess;


public class main extends resource{

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
	
			
		//调度执行场景测试
//		testRun testrun = new testRun();
//		testrun.run("main");
		
		//test
		pipelineTestProcess test = new pipelineTestProcess(BASEURL+"/login.htm",0,"测试用的channel");
		test.viewPipeline();
		
		
		//执行用例抽取
//		extractCase excase = new extractCase();
//		excase.extract("datasource");//all,datasource,datatable,canal,masterslave,channel,pipeline
		
//创建映射关系
//		mappingConfigProcess mapping = new mappingConfigProcess(BASEURL+"/login.htm","source$$part_2");
//		mapping.insertMapping();
		
//主备配置
//		masterslaveConfigProcess masterslave = new masterslaveConfigProcess(BASEURL+"/login.htm","everdb_hash");
//		masterslave.insertMasterSlave();
		
//canal操作
//		canalConfigProcess canal = new canalConfigProcess(BASEURL+"/login.htm",0);
//		canal.insertCanal();
//		canalViewProcess canalv = new canalViewProcess(BASEURL+"/login.htm","要查的canal名字");
//		canal.viewCannl();
		
//pipeline操作
//		pipelineConfigProcess pipeline = new pipelineConfigProcess(BASEURL+"/login.htm");
//		pipeline.insertPipeline();
//		pipeline.viewPipeline();
		
		
		
//启动channel
//		channelConfigProcess channelconfig = new channelConfigProcess(BASEURL+"/login.htm",1);
//		channelconfig.insertChannel();
//		channelStartupProcess channelstart = new channelStartupProcess(BASEURL+"/login.htm");
//		channelstart.startupChannel();
		
		
		
		
//配置数据表
//		datatableConfigProcess datatableconfig = new datatableConfigProcess(BASEURL+"/login.htm",1);
//		datatableconfig.cfgDatatable();
		
		
//配置数据源
//		datasourceCongfigProcess process = new datasourceCongfigProcess(BASEURL+"/login.htm");
//		process.cfgDatasource();
//查看数据源信息		
//		datasourceViewProcess viewProcess = new datasourceViewProcess("http://172.16.110.33:8080/login.htm","bbb");
//		viewProcess.viewDatasource();
	}

}
