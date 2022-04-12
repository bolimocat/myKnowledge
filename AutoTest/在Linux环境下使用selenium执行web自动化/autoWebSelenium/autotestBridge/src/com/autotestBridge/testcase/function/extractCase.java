//统一抽取文件中的用例，写入对应的表。
package com.autotestBridge.testcase.function;

import java.util.ArrayList;

import com.autotestBridge.dom.dbLink;
import com.autotestBridge.function.commonkit;
import com.autotestBridge.resource.resource;

public class extractCase extends resource{

	
	
	
	
	
	/**
	 * 执行抽取
	 * @param whichCase
	 */
	public void extract(String whichCase) {
//		boolean result = false;
		
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
		casePickupCase pickCase = new casePickupCase(host,port,database,user,pass);
		
		int ex = 0;
		if(whichCase.equals("datasource")) {
			ex = 1;
		}
		if(whichCase.equals("datatable")) {
			ex = 2;
		}
		if(whichCase.equals("canal")) {
			ex = 3;
		}
		if(whichCase.equals("masterslave")) {
			ex = 4;
		}
		if(whichCase.equals("channel")) {
			ex = 5;
		}
		if(whichCase.equals("pipeline")) {
			ex = 6;
		}
		if(whichCase.equals("all")) {
			ex = 0;
		}
		
		switch(ex) {
		case	0:
			pickCase.pickupDatasource(CASEPTH+""+DSCASE);
			pickCase.pickupDatatable(CASEPTH+""+TBCASE);
			pickCase.pickupCanal(CASEPTH+""+CLCASE);
			pickCase.pickupMasterSlave(CASEPTH+""+MSCASE);
			pickCase.pickupChannel(CASEPTH+""+CHCASE);
			pickCase.pickupPipeline(CASEPTH+""+PICASE);
			break;
		case 1:
			pickCase.pickupDatasource(CASEPTH+""+DSCASE);
			break;
		case 2:
			pickCase.pickupDatatable(CASEPTH+""+TBCASE);
			break;
		case 3:
			pickCase.pickupCanal(CASEPTH+""+CLCASE);
			break;
		case 4:
			pickCase.pickupMasterSlave(CASEPTH+""+MSCASE);
			break;
		case 5:
			pickCase.pickupChannel(CASEPTH+""+CHCASE);
			break;
		case 6:
			pickCase.pickupPipeline(CASEPTH+""+PICASE);
			break;
			default : 
				System.out.println("选择加载哪个用例文件。");
				break;
		}

	}


	//将抽取的用例组合到testcase表


}
