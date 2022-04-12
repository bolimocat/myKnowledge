//实施用例
package com.autotestBridge.testcase.function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.autotestBridge.testcase.dom.caseCanalBean;
import com.autotestBridge.testcase.dom.caseChannelBean;
import com.autotestBridge.testcase.dom.caseDataSourceBean;
import com.autotestBridge.testcase.dom.caseDataTableBean;
import com.autotestBridge.testcase.dom.casePipelineBean;
import com.autotestBridge.testcase.dom.caseTestCaseBean;

public class caseImplement {

	
	//定义数据库操作
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		
		private String host;
		private String port;
		private String user;
		private String pass;
		private String database;
	
	public caseImplement(String h,String u,String p,String pt,String db) {
		host = h;
		port = pt;
		user = u;
		pass = p;
		database = db;
	}
	
	/**
	 * 读取全局可执行用例
	 * @return
	 */
	public ArrayList<caseTestCaseBean> caseList() {
		ArrayList<caseTestCaseBean> list = new ArrayList<caseTestCaseBean>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+host+":"+port+"/"+database+"";
			ct = DriverManager.getConnection(url, user, pass);
			ps = ct.prepareStatement("select name,source_datasource,target_datasource,source_datatable,target_datatable,canal,master_slave,channelinfo,pipeline,active from testcase where active = 1;");
			rs = ps.executeQuery();
			while(rs.next()) {
				caseTestCaseBean testcase = new caseTestCaseBean();
				testcase.setName(rs.getString("name"));
				testcase.setSource_datasource(rs.getInt("source_datasource"));
				testcase.setTarget_datasource(rs.getInt("target_datasource"));
//				testcase.setData_table(rs.getInt("data_table"));
				testcase.setSource_datatable(rs.getInt("source_datatable"));
				testcase.setTarget_datatable(rs.getInt("target_datatable"));
				testcase.setCanal(rs.getInt("canal"));
				testcase.setMaster_slave(rs.getInt("master_slave"));
				testcase.setChannelinfo(rs.getInt("channelinfo"));
				testcase.setPipeline(rs.getInt("pipeline"));
				list.add(testcase);
			}
			}
		catch (Exception caseList) {
			caseList.printStackTrace();
		}
		finally{
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 按id读取datasource的执行用例
	 * @param id
	 * @return
	 */
	public ArrayList<caseDataSourceBean> datasourceList(int id) {
		ArrayList<caseDataSourceBean> list = new ArrayList<caseDataSourceBean>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+host+":"+port+"/"+database+"";
			ct = DriverManager.getConnection(url, user, pass);
			ps = ct.prepareStatement("select name,ds_type,ds_user,ds_pass,ds_url,ds_code,ds_pool from datasource where id = "+id+";");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				caseDataSourceBean ds = new caseDataSourceBean();
				ds.setName(rs.getString("name"));
				ds.setDs_type(rs.getString("ds_type"));
				ds.setDs_user(rs.getString("ds_user"));
				ds.setDs_pass(rs.getString("ds_pass"));
				ds.setDs_url(rs.getString("ds_url"));
				ds.setDs_code(rs.getString("ds_code"));
				ds.setDs_pool(rs.getString("ds_pool"));
				list.add(ds);
			}
			}
		catch (Exception datasourceList) {
			datasourceList.printStackTrace();
		}
		finally{
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 按id读取datatable的执行用例
	 * @param id
	 * @return
	 */
	public ArrayList<caseDataTableBean> datatableList(int id) {
		ArrayList<caseDataTableBean> list = new ArrayList<caseDataTableBean>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+host+":"+port+"/"+database+"";
			ct = DriverManager.getConnection(url, user, pass);
			ps = ct.prepareStatement("select db,tb,ds_name from datatable where id = "+id+";");
			rs = ps.executeQuery();
			while(rs.next()) {
				caseDataTableBean table = new caseDataTableBean();
				table.setDb(rs.getString("db"));
				table.setTb(rs.getString("tb"));
				table.setDs_name(rs.getString("ds_name"));
				list.add(table);
			}
			}
		catch (Exception datasourceList) {
			datasourceList.printStackTrace();
		}
		finally{
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 按id读取canal的执行用例
	 * @param id
	 * @return
	 */
	public ArrayList<caseCanalBean> canalList(int id) {
		ArrayList<caseCanalBean> list = new ArrayList<caseCanalBean>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+host+":"+port+"/"+database+"";
			ct = DriverManager.getConnection(url, user, pass);
			ps = ct.prepareStatement("select canal_name,run_mode,zkcluster,ds_type,db_url,db_user,db_pass,db_code,position_setup,position_startup,position_info,tsdb_startup,rdsaccesskey,rdssecretkey,rdsinstanceid,store_type,batch_type,mem_buffer_record,mem_buffer_unit,HA_type,media_group_key,heartbeat_startup,heartbeat_sql,heartbeat_fequence,heartbeat_overtime,heartbeat_retry,heartbeat_ha,other_parameters,meta_type,index_type,service_port,default_overtime,sendBufferSize,receiveBufferSize,switch_backtime,filter_expression,describe_info from canal where id =  "+id+";");
			rs = ps.executeQuery();
			while(rs.next()) {
				caseCanalBean canal = new caseCanalBean();
				canal.setCanal_name(rs.getString("canal_name"));
				canal.setRun_mode(rs.getInt("run_mode"));
				canal.setZkcluster(rs.getString("zkcluster"));
				canal.setDs_type(rs.getInt("ds_type"));
				canal.setDb_url(rs.getString("db_url"));
				canal.setDb_url(rs.getString("db_user"));
				canal.setDb_pass(rs.getString("db_pass"));
				canal.setDb_code(rs.getString("db_code"));
				canal.setPosition_setup(rs.getInt("position_setup"));
				canal.setPosition_startup(rs.getInt("position_startup"));
				canal.setPosition_info(rs.getString("position_info"));
				canal.setTsdb_startup(rs.getInt("tsdb_startup"));
				canal.setRdsaccesskey(rs.getString("rdsaccesskey"));
				canal.setRdssecretkey(rs.getString("rdssecretkey"));
				canal.setRdsinstanceid(rs.getString("rdsinstanceid"));
				canal.setStore_type(rs.getInt("store_type"));
				canal.setBatch_type(rs.getInt("batch_type"));
				canal.setMem_buffer_record(rs.getInt("mem_buffer_record"));
				canal.setMem_buffer_unit(rs.getInt("mem_buffer_unit"));
				canal.setHA_type(rs.getInt("HA_type"));
				canal.setMedia_group_key(rs.getString("media_group_key"));
				canal.setHeartbeat_startup(rs.getBoolean("heartbeat_startup"));
				canal.setHeartbeat_sql(rs.getString("heartbeat_sql"));		
				canal.setHeartbeat_fequence(rs.getInt("heartbeat_fequence"));
				canal.setHeartbeat_overtime(rs.getInt("heartbeat_overtime"));
				canal.setHeartbeat_retry(rs.getInt("heartbeat_retry"));
				canal.setHeartbeat_ha(rs.getBoolean("heartbeat_ha"));
				canal.setOther_parameters(rs.getBoolean("other_parameters"));
				canal.setMeta_type(rs.getInt("meta_type"));
				canal.setIndex_type(rs.getInt("index_type"));
				canal.setService_port(rs.getInt("service_port"));
				canal.setDefault_overtime(rs.getInt("default_overtime"));
				canal.setSendBufferSize(rs.getInt("sendBufferSize"));
				canal.setReceiveBufferSize(rs.getInt("receiveBufferSize"));
				canal.setSwitch_backtime(rs.getInt("switch_backtime"));
				canal.setFilter_expression(rs.getString("filter_expression"));
				canal.setDescribe_info(rs.getString("describe_info"));
				list.add(canal);
				
			}
			}
		catch (Exception datasourceList) {
			datasourceList.printStackTrace();
		}
		finally{
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		return list;
	}

	
	/**
	 * 按id读取channel_info的用例内容
	 * @param id
	 * @return
	 */
	public ArrayList<caseChannelBean> channelList(int id) {
		ArrayList<caseChannelBean> list = new ArrayList<caseChannelBean>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+host+":"+port+"/"+database+"";
			ct = DriverManager.getConnection(url, user, pass);
			ps = ct.prepareStatement("select chname,sync_accord,sync_mode,accord_start,accord_algorithm,requery_latency,describe_info from channelinfo where id =  "+id+";");
			rs = ps.executeQuery();
			while(rs.next()) {
				caseChannelBean channel = new caseChannelBean();
				channel.setChname(rs.getString("chname"));
				channel.setSync_accord(rs.getInt("sync_accord"));
				channel.setSync_mode(rs.getInt("sync_mode"));;
				channel.setAccord_start(rs.getInt("accord_start"));;
				channel.setAccord_algorithm(rs.getInt("accord_algorithm"));;
				channel.setRequery_latency(rs.getInt("requery_latency"));;
				channel.setDescribe_info(rs.getString("describe_info"));;
				list.add(channel);
				
			}
			}
		catch (Exception datasourceList) {
			datasourceList.printStackTrace();
		}
		finally{
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		return list;
	}

	
	//按ID读取pipeline的用例内容
	public ArrayList<casePipelineBean> pipelineList(int id) {
		ArrayList<casePipelineBean> list = new ArrayList<casePipelineBean>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+host+":"+port+"/"+database+"";
			ct = DriverManager.getConnection(url, user, pass);
			ps = ct.prepareStatement("select chname,pipelinename,select_node,load_node,parallelism,oppositethread,loaddatathread,loadfilethread,isClicked,isMainWeb,canalName,consumption,overtime,describe_info,issuper,isBatch,batchsize,ignoreSelect,ignoreLoad,arbiter,loadbalance,transport,selectlog,selectlogdetail,loadlog,dryrunmode,supportddl,ddlView,ddlFunction,ddlProcedure,ddlEvent,ddlTriger,jumbddlwrong,fileduplicate,transportencry,publicnet,jumpfreegate,jumpreversequery,tbtypetrans,compatiblesync,customsyncsign,casename from pipeline  where id =  "+id+";");
			rs = ps.executeQuery();
			while(rs.next()) {
				casePipelineBean pip = new casePipelineBean();
				pip.setChname(rs.getString("chname"));;
				pip.setPipelinename(rs.getString("pipelinename")); ;
				pip.setSelect_node(rs.getBoolean("select_node")); 
				pip.setLoad_node(rs.getBoolean("load_node"));
				pip.setParallelism(rs.getInt("parallelism")); ;
				pip.setOppositethread(rs.getInt("oppositethread")); 
				pip.setLoaddatathread(rs.getInt("loaddatathread"));
				pip.setLoadfilethread(rs.getInt("loadfilethread")); ;
				pip.setIsClicked(rs.getInt("isClicked")); 
				pip.setIsMainWeb(rs.getInt("isMainWeb"));
				pip.setCanalName(rs.getString(""));
				
				pip.setConsumption(rs.getInt("consumption")); 
				
				pip.setOvertime(rs.getInt("overtime")); 
				pip.setDescribe_info(rs.getString("describe_info"));
				pip.setIssuper(rs.getBoolean("issuper"));
				pip.setIsBatch(rs.getInt("isBatch"));
				pip.setBatchsize(rs.getInt("batchsize"));
				pip.setIgnoreSelect(rs.getInt("ignoreSelect"));
				pip.setIgnoreLoad(rs.getInt("ignoreLoad")); ;
				pip.setArbiter(rs.getString("arbiter"));
				pip.setLoadbalance(rs.getString("loadbalance")); ;
				pip.setTransport(rs.getString("transport"));
				pip.setSelectlog(rs.getInt("selectlog"));
				pip.setSelectlogdetail(rs.getInt("selectlogdetail"));
				pip.setLoadlog(rs.getInt("loadlog"));
				pip.setDryrunmode(rs.getInt("dryrunmode"));
				pip.setSupportddl(rs.getInt("supportddl"));
				pip.setDdlView(rs.getBoolean("ddlView"));
				
				pip.setDdlFunction(rs.getBoolean("ddlFunction"));
				pip.setDdlProcedure(rs.getBoolean("ddlProcedure"));
				pip.setDdlEvent(rs.getBoolean("ddlEvent"));
				pip.setDdlTriger(rs.getBoolean("ddlTriger"));
				pip.setJumbddlwrong(rs.getBoolean("jumbddlwrong"));
				pip.setFileduplicate(rs.getBoolean("fileduplicate"));
				pip.setTransportencry(rs.getBoolean("transportencry"));
				pip.setPublicnet(rs.getBoolean("publicnet"));
				pip.setJumpfreegate(rs.getBoolean("jumpfreegate"));
				pip.setJumpreversequery(rs.getBoolean("jumpreversequery"));
				pip.setTbtypetrans(rs.getBoolean("tbtypetrans"));
				pip.setCompatiblesync(rs.getBoolean("compatiblesync")); 
				pip.setCustomsyncsign(rs.getString("customsyncsign"));
				list.add(pip);
			}
			}
		catch (Exception datasourceList) {
			datasourceList.printStackTrace();
		}
		finally{
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		return list;
	}
}
