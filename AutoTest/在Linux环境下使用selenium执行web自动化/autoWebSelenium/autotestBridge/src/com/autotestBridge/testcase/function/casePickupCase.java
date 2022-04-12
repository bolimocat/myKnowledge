//从csv文件中读取基础用例内容
package com.autotestBridge.testcase.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.autotestBridge.testcase.dom.caseDataSourceBean;



public class casePickupCase {
	
	//定义数据库操作
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		
		private String Host;
		private String Port;
		private String Database;
		private String User;
		private String Pass;
		
		public casePickupCase(String h,String port,String db,String u,String p) {
			Host = h;
			Port = port;
			Database = db;
			User = u;
			Pass = p;
		}

		/**
		 * 从文件读取加载datasource内容
		 * @param filepath
		 */
		@SuppressWarnings("resource")
		public void pickupDatasource(String filepath) {
			try {
				@SuppressWarnings("unused")
				File file = new File(filepath);
				BufferedReader reader = null;
				String line = null;
				reader = new BufferedReader(new FileReader(filepath));
				while((line = reader.readLine())!=null) {
					if(!(line.startsWith("#"))) {
						
						String[] datasourceArray = line.split(",");
						Class.forName("com.mysql.cj.jdbc.Driver");
						ct = DriverManager.getConnection("jdbc:mysql://"+Host+":"+Port+"/"+Database+"?characterEncoding=utf8", User, Pass);
						ps = ct.prepareStatement("insert into datasource (name,ds_type,ds_user,ds_pass,ds_url,ds_code,ds_pool) values ('"+datasourceArray[0]+"','"+datasourceArray[1]+"','"+datasourceArray[2]+"',"
								+ "'"+datasourceArray[3]+"','"+datasourceArray[4]+"','"+datasourceArray[5]+"','"+datasourceArray[6]+"');");
						
						if(ps.executeUpdate()>0) {
							System.out.println("【写入数据正确】： "+datasourceArray[0]+"','"+datasourceArray[1]+"','"+datasourceArray[2]+"',"
									+ "'"+datasourceArray[3]+"','"+datasourceArray[4]+"','"+datasourceArray[5]+"','"+datasourceArray[6]);
						}
						else {
							System.out.println("【写入数据失败】： "+datasourceArray[0]+"','"+datasourceArray[1]+"','"+datasourceArray[2]+"',"
									+ "'"+datasourceArray[3]+"','"+datasourceArray[4]+"','"+datasourceArray[5]+"','"+datasourceArray[6]);
						}
					}
					else {
						System.out.println("当前行内容注释，不执行加载。");
					}
					
					
				}
			} catch (Exception pickupDatasource) {
				pickupDatasource.printStackTrace();
			}

		}
		
		/**
		 * 从文件读取datatable内容
		 * @param filepath
		 */
		@SuppressWarnings("resource")
		public void pickupDatatable(String filepath) {
			try {
				@SuppressWarnings("unused")
				File file = new File(filepath);
				BufferedReader reader = null;
				String line = null;
				reader = new BufferedReader(new FileReader(filepath));
				while((line = reader.readLine())!=null) {
					if(!(line.startsWith("#"))) {
						String[] datatableArray = line.split(",");
						Class.forName("com.mysql.cj.jdbc.Driver");
						ct = DriverManager.getConnection("jdbc:mysql://"+Host+":"+Port+"/"+Database+"?characterEncoding=utf8", User, Pass);
						ps = ct.prepareStatement("insert into datatable (db,tb,ds_name) values ('"+datatableArray[0]+"','"+datatableArray[1]+"','"+datatableArray[2]+"')");
						
						if(ps.executeUpdate()>0) {
							System.out.println("【写入数据正确】： "+datatableArray[0]+"','"+datatableArray[1]+"','"+datatableArray[2]);
						}
						else {
							System.out.println("【写入数据失败】： "+datatableArray[0]+"','"+datatableArray[1]+"','"+datatableArray[2]);
						}
					}
					else {
						System.out.println("当前行内容注释，不执行加载。");
					}
					
					
				}
			} catch (Exception pickDatatable) {
				pickDatatable.printStackTrace();
			}

		}
		
		/**
		 * 从文件读取canal内容
		 * @param filepath
		 */
		@SuppressWarnings("resource")
		public void pickupCanal(String filepath) {
			try {
				@SuppressWarnings("unused")
				File file = new File(filepath);
				BufferedReader reader = null;
				String line = null;
				reader = new BufferedReader(new FileReader(filepath));
				while((line = reader.readLine())!=null) {
					if(!(line.startsWith("#"))) {
						String[] canalArray = line.split(",");
						Class.forName("com.mysql.cj.jdbc.Driver");
						ct = DriverManager.getConnection("jdbc:mysql://"+Host+":"+Port+"/"+Database+"?characterEncoding=utf8", User, Pass);
					   ps = ct.prepareStatement("insert into canal (canal_name,run_mode,zkcluster,ds_type,db_url,db_user,db_pass,db_code,position_setup,position_startup,position_info,tsdb_startup,rdsaccesskey,rdssecretkey,rdsinstanceid,store_type,batch_type,mem_buffer_record,mem_buffer_unit,HA_type,media_group_key,heartbeat_startup,heartbeat_sql,heartbeat_fequence,heartbeat_overtime,heartbeat_retry,heartbeat_ha,other_parameters,meta_type,index_type,service_port,default_overtime,sendBufferSize,receiveBufferSize,switch_backtime,filter_expression,describe_info) values ('"+canalArray[0]+"','"+canalArray[1]+"','"+canalArray[2]+"','"+canalArray[3]+"','"+canalArray[4]+"','"+canalArray[5]+"','"+canalArray[6]+"','"+canalArray[7]+"','"+canalArray[8]+"','"+canalArray[9]+"','"+canalArray[10]+"','"+canalArray[11]+"','"+canalArray[12]+"','"+canalArray[13]+"','"+canalArray[14]+"','"+canalArray[15]+"','"+canalArray[16]+"','"+canalArray[17]+"','"+canalArray[18]+"','"+canalArray[19]+"','"+canalArray[20]+"','"+canalArray[21]+"','"+canalArray[22]+"','"+canalArray[23]+"','"+canalArray[24]+"','"+canalArray[25]+"','"+canalArray[26]+"','"+canalArray[27]+"','"+canalArray[28]+"','"+canalArray[29]+"','"+canalArray[30]+"','"+canalArray[31]+"','"+canalArray[32]+"','"+canalArray[33]+"','"+canalArray[34]+"','"+canalArray[35]+"','"+canalArray[36]+"')");
						
						if(ps.executeUpdate()>0) {
							System.out.println("【写入数据正确】： "+canalArray[0]+"','"+canalArray[1]+"','"+canalArray[2]);
						}
						else {
							System.out.println("【写入数据失败】： "+canalArray[0]+"','"+canalArray[1]+"','"+canalArray[2]);
						}
					}
					else {
						System.out.println("当前行内容注释，不执行加载。");
					}
					
					
				}
			} catch (Exception pickupCanal) {
				pickupCanal.printStackTrace();
			}
		}
		
		/**
		 * 从文件读取masterslave内容
		 * @param filepath
		 */
		@SuppressWarnings("resource")
		public void pickupMasterSlave(String filepath) {
			try {
				@SuppressWarnings("unused")
				File file = new File(filepath);
				BufferedReader reader = null;
				String line = null;
				reader = new BufferedReader(new FileReader(filepath));
				while((line = reader.readLine())!=null) {
					if(!(line.startsWith("#"))) {
						String[] msArray = line.split(",");
						Class.forName("com.mysql.cj.jdbc.Driver");
						ct = DriverManager.getConnection("jdbc:mysql://"+Host+":"+Port+"/"+Database+"?characterEncoding=utf8", User, Pass);
					   ps = ct.prepareStatement("insert into masterslave (source_info,master_info,describe_info) values ('"+msArray[0]+"','"+msArray[1]+"','"+msArray[2]+"');");
						
						if(ps.executeUpdate()>0) {
							System.out.println("【写入数据正确】： "+msArray[0]+"','"+msArray[1]+"','"+msArray[2]);
						}
						else {
							System.out.println("【写入数据失败】： "+msArray[0]+"','"+msArray[1]+"','"+msArray[2]);
						}
					}
					else {
						System.out.println("当前行内容注释，不执行加载。");
					}
					
					
				}
			} catch (Exception pickupMasterSlave) {
				pickupMasterSlave.printStackTrace();
			}
		}

		/**
		 * 从文件读取channel内容
		 * @param filepath
		 */
		@SuppressWarnings("resource")
		public void pickupChannel(String filepath) {
			try {
				@SuppressWarnings("unused")
				File file = new File(filepath);
				BufferedReader reader = null;
				String line = null;
				reader = new BufferedReader(new FileReader(filepath));
				while((line = reader.readLine())!=null) {
					if(!(line.startsWith("#"))) {
						String[] chArray = line.split(",");
						Class.forName("com.mysql.cj.jdbc.Driver");
						ct = DriverManager.getConnection("jdbc:mysql://"+Host+":"+Port+"/"+Database+"?characterEncoding=utf8", User, Pass);
					   ps = ct.prepareStatement("insert into channelinfo (chname,sync_accord,sync_mode,accord_start,accord_algorithm,requery_latency,describe_info) values ('"+chArray[0]+"','"+chArray[1]+"','"+chArray[2]+"','"+chArray[3]+"','"+chArray[4]+"','"+chArray[5]+"','"+chArray[6]+"'); ");
						
						if(ps.executeUpdate()>0) {
							System.out.println("【写入数据正确】： "+chArray[0]+"','"+chArray[1]+"','"+chArray[2]);
						}
						else {
							System.out.println("【写入数据失败】： "+chArray[0]+"','"+chArray[1]+"','"+chArray[2]);
						}
					}
					else {
						System.out.println("当前行内容注释，不执行加载。");
					}
					
					
				}
			} catch (Exception pickupChannel) {
				pickupChannel.printStackTrace();
			}
		}
		
		/**
		 * 从文件读取pipeline内容
		 * @param filepath
		 */
		@SuppressWarnings("resource")
		public void pickupPipeline(String filepath) {
			try {
				@SuppressWarnings("unused")
				File file = new File(filepath);
				BufferedReader reader = null;
				String line = null;
				reader = new BufferedReader(new FileReader(filepath));
				while((line = reader.readLine())!=null) {
					if(!(line.startsWith("#"))) {
						String[] pipArray = line.split(",");
						Class.forName("com.mysql.cj.jdbc.Driver");
						ct = DriverManager.getConnection("jdbc:mysql://"+Host+":"+Port+"/"+Database+"?characterEncoding=utf8", User, Pass);
					   ps = ct.prepareStatement("insert into pipeline (chname,pipelinename,select_node,load_node,parallelism,oppositethread,loaddatathread,loadfilethread,isClicked,isMainWeb,canalName,consumption,overtime,describe_info,issuper,isBatch,batchsize,ignoreSelect,ignoreLoad,arbiter,loadbalance,transport,selectlog,selectlogdetail,loadlog,dryrunmode,supportddl,ddlView"
					   		+ ",ddlFunction,ddlProcedure,ddlEvent,ddlTriger,jumbddlwrong,fileduplicate,transportencry,publicnet,jumpfreegate,jumpreversequery,tbtypetrans,compatiblesync,customsyncsign"
					   		+ ") values ('"+pipArray[0]+"','"+pipArray[1]+"','"+pipArray[2]+"','"+pipArray[3]+"','"+pipArray[4]+"','"+pipArray[5]+"','"+pipArray[6]+"','"+pipArray[7]+"','"+pipArray[8]+"','"+pipArray[9]+"',"
					   				+ "'"+pipArray[10]+"','"+pipArray[11]+"','"+pipArray[12]+"','"+pipArray[13]+"','"+pipArray[14]+"','"+pipArray[15]+"','"+pipArray[16]+"','"+pipArray[17]+"','"+pipArray[18]+"','"+pipArray[19]+"','"+pipArray[20]+"',"
					   				+ "'"+pipArray[21]+"','"+pipArray[22]+"','"+pipArray[23]+"','"+pipArray[24]+"','"+pipArray[25]+"','"+pipArray[26]+"','"+pipArray[27]+"','"+pipArray[28]+"','"+pipArray[29]+"','"+pipArray[30]+"'"
					   						+ ",'"+pipArray[31]+"','"+pipArray[32]+"','"+pipArray[33]+"','"+pipArray[34]+"','"+pipArray[35]+"','"+pipArray[36]+"','"+pipArray[37]+"','"+pipArray[38]+"','"+pipArray[39]+"','"+pipArray[40]+"');");
						
						if(ps.executeUpdate()>0) {
							System.out.println("【写入数据正确】： "+pipArray[0]+"','"+pipArray[1]+"','"+pipArray[2]);
						}
						else {
							System.out.println("【写入数据失败】： "+pipArray[0]+"','"+pipArray[1]+"','"+pipArray[2]);
						}
					}
					else {
						System.out.println("当前行内容注释，不执行加载。");
					}
					
					
				}
			} catch (Exception pickupPipeline) {
				pickupPipeline.printStackTrace();
			}
		}

}


