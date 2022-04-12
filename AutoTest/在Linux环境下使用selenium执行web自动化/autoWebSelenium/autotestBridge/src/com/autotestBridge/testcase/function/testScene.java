package com.autotestBridge.testcase.function;

import java.util.ArrayList;

import com.autotestBridge.resource.resource;
import com.autotestBridge.testcase.dom.caseTestCaseBean;

import canalGuiTest.canalConfigProcess;
import channelmanagerGuiTest.channelConfigProcess;
import datasourceGuiTest.datasourceCongfigProcess;
import datatableGuiTest.datatableConfigProcess;
import masterslaveGuiTest.masterslaveConfigProcess;
import pipelinemanagerGuiTest.pipelineConfigProcess;

public class testScene extends resource{
	
	private String host;
	private String port;
	private String user;
	private String pass;
	private String database;
	
	public testScene(String h,String u,String p,String pt,String db) {
		host = h;
		port = pt;
		user = u;
		pass = p;
		database = db;
	}

	//执行主线程测试场景
	public void executeMain(ArrayList testcase) throws InterruptedException {
		System.out.println("开始主线测试场景");
		for(int i=0;i<testcase.size();i++) {
			caseTestCaseBean caselist = (caseTestCaseBean)testcase.get(i);
			//写入源端数据源
			int source_ds_id = caselist.getSource_datasource();
			datasourceCongfigProcess dsprocess1 = new datasourceCongfigProcess(BASEURL+"/login.htm",source_ds_id);
			dsprocess1.cfgDatasource();
			
			//写入目标端数据源
			int target_ds_id = caselist.getTarget_datasource();
			datasourceCongfigProcess dsprocess2 = new datasourceCongfigProcess(BASEURL+"/login.htm",target_ds_id);
			dsprocess2.cfgDatasource();
			
			//配置源数据表
			int source_tb_id = caselist.getSource_datatable();
			datatableConfigProcess tableprocess1 = new datatableConfigProcess(BASEURL+"/login.htm",source_tb_id);
			tableprocess1.cfgDatatable();
			
			//配置目标数据表
			int target_tb_id = caselist.getTarget_datatable();
			datatableConfigProcess tableprocess2 = new datatableConfigProcess(BASEURL+"/login.htm",target_tb_id);
			tableprocess2.cfgDatatable();
			
			//配置主备关系
			caseTransferTool casetool = new caseTransferTool(host,user,pass,port,database);
			String master_datasource = casetool.fetchMasterinfo(caselist.getMaster_slave());
			masterslaveConfigProcess masterslaveprocess = new masterslaveConfigProcess(BASEURL+"/login.htm",master_datasource);
			masterslaveprocess.insertMasterSlave();
			
			//配置canal
			int canal_id = caselist.getCanal();
			canalConfigProcess canal = new canalConfigProcess(BASEURL+"/login.htm",canal_id);
			canal.insertCanal();
			
			
			//配置channel
			int channel_id = caselist.getChannelinfo();
			channelConfigProcess channel = new channelConfigProcess(BASEURL+"/login.htm",channel_id);
			channel.insertChannel();
			
			//配置pipeline
			int pipeline_id = caselist.getPipeline();
			pipelineConfigProcess pipeline = new pipelineConfigProcess(BASEURL+"/login.htm",pipeline_id,"测试用的channel");
			pipeline.insertPipeline();
		}
	}
}
