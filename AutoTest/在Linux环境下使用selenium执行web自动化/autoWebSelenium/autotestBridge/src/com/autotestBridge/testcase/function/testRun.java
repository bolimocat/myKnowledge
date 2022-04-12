package com.autotestBridge.testcase.function;

import java.util.ArrayList;

import com.autotestBridge.dom.dbLink;
import com.autotestBridge.function.commonkit;
import com.autotestBridge.resource.resource;

public class testRun extends resource{

	//执行测试
	public void run(String testScene) throws InterruptedException {
		
		//加载数据库连接
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
		
		
		//实现全局用例读取，testcase表
		caseImplement caseImpl = new caseImplement(host,user,pass,port,database);
		//全局用例存入globalcase，每个位置存储分表id
		ArrayList globalcase = new ArrayList();
		globalcase = caseImpl.caseList();
		
		//根据输入选择执行场景
			testScene test = new testScene(host,user,pass,port,database);
			if(testScene.equals("main")) {
				//主线测试任务
				test.executeMain(globalcase);
			}
			if(testScene.equals("分支")) {
				//分支测试任务
				
			}
				
	}
}
