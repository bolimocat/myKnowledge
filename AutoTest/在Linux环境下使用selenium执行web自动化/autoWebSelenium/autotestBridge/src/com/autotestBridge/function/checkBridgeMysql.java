//查看前端操作引起的系统Mysql表变化
package com.autotestBridge.function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.autotestBridge.dom.channelBean;
import com.autotestBridge.dom.datasourceBean;

public class checkBridgeMysql {

	//定义数据库操作
	PreparedStatement ps = null;
	Connection ct = null;
	ResultSet rs = null;
	
	private String host;
	private String port;
	private String user;
	private String pass;
	private String database;
	
	public checkBridgeMysql(String h,String u,String p,String pt,String db) {
		host = h;
		port = pt;
		user = u;
		pass = p;
		database = db;
	}
	
	/**
	 * 根据输入的数据源名称获得系统mysql表中的内容，由其是该条记录的ID
	 * @param datasourceName
	 * @return
	 */
	public int datasourceId(String datasourceName) {
		int id = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+host+":"+port+"/"+database+"";
			ct = DriverManager.getConnection(url, user, pass);
			ps = ct.prepareStatement("select id from data_media_source where name = '"+datasourceName+"';");
			rs = ps.executeQuery();
			while(rs.next()) {
			
				id = rs.getInt("id");
				System.out.println("=== "+rs.getInt("id"));
			}
			}
		catch (Exception datasourceId) {
			datasourceId.printStackTrace();
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
		
		return id;
	}
	
	
	/**
	 * 根据sourceid，获得完整的datasource信息
	 * @param datasourceId
	 * @return
	 */
	public ArrayList<datasourceBean> datasourceInfo(int datasourceId) {
		ArrayList<datasourceBean> info = new ArrayList<datasourceBean>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+host+":"+port+"/"+database+"";
			ct = DriverManager.getConnection(url, user, pass);
			ps = ct.prepareStatement("select name,type,properties  from data_media_source where id = "+datasourceId );
			rs = ps.executeQuery();
			while(rs.next()) {
				datasourceBean sourceBean = new datasourceBean();
				sourceBean.setNAME(rs.getString("name"));
				sourceBean.setTYPE(rs.getString("type"));
				sourceBean.setPROPERTIES(rs.getString("properties"));
				info.add(sourceBean);
			}
			}
		catch (Exception datasourceUpdate) {
			datasourceUpdate.printStackTrace();
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
		return info;
	}


	/**
	 * 根据channel名称，获得该channel的全部信息
	 * @param name
	 * @return
	 */
	public ArrayList<channelBean> channelInfo(String name) {
		ArrayList<channelBean> info = new ArrayList<channelBean>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+host+":"+port+"/"+database+"";
			ct = DriverManager.getConnection(url, user, pass);
			ps = ct.prepareStatement("select id,name,description,parameters,gmt_create,gmt_modified from channel where name = '"+name+"';" );
			rs = ps.executeQuery();
			while(rs.next()) {
				channelBean channel = new channelBean();
				channel.setId(rs.getInt("id"));
				channel.setName(rs.getString("name"));
				channel.setDescription(rs.getString("description"));
				channel.setParameters(rs.getString("parameters"));
				channel.setGmt_create(rs.getString("gmt_create"));
				channel.setGmt_modified(rs.getString("gmt_modified"));
				info.add(channel);
			}
			}
		catch (Exception fetchchannelInfo) {
			fetchchannelInfo.printStackTrace();
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
		return info;
	}

	/**
	 * 通过channel的name获得该channel的id
	 * @param name
	 * @return
	 */
	public int channelId(String name) {
		int id = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+host+":"+port+"/"+database+"";
			ct = DriverManager.getConnection(url, user, pass);
			ps = ct.prepareStatement("select id  from channel where name = '"+name+"';");
			rs = ps.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id");
			}
			}
		catch (Exception channelId) {
			channelId.printStackTrace();
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
		return id;
	}


	/**
	 * 通过channel的id，判断该channel是否已经有pipeline,true可建pipeline
	 * @param id
	 * @return
	 */
	public boolean havePip(int id) {
		boolean result = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+host+":"+port+"/"+database+"";
			ct = DriverManager.getConnection(url, user, pass);
			ps = ct.prepareStatement("select count(id)  from pipeline where channel_id = "+id+";");
			rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1)==0) {
					result = true;
				}
				if(rs.getInt(1)!=0) {
					result = false;
				}
			}
			}
		catch (Exception channelId) {
			channelId.printStackTrace();
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
		return result;
	}

	/**
	 * 通过channel的id，获得他所包含的pipeline的id
	 * @param channelId
	 * @return
	 */
	public int pipelineId(int channelId) {
		int id = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+host+":"+port+"/"+database+"";
			ct = DriverManager.getConnection(url, user, pass);
			ps = ct.prepareStatement("select id  from pipeline where channel_id = "+channelId);
			rs = ps.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id");
			}
			}
		catch (Exception pipelineId) {
			pipelineId.printStackTrace();
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
		return id;
	}

	/**
	 * 通过pipeline名称获得pipeline的id
	 * @param name
	 * @return
	 */
	public int pipelineId1(String name) {
		int id = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+host+":"+port+"/"+database+"";
			ct = DriverManager.getConnection(url, user, pass);
			ps = ct.prepareStatement("select id  from pipeline where name = '"+name+"';");
			rs = ps.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id");
			}
			}
		catch (Exception pipelineId) {
			pipelineId.printStackTrace();
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
		return id;
	}
	
	/**
	 * 通过cannal的名称获得该cannal的id
	 * @param name
	 * @return
	 */
	public int canalId(String name) {
		int id = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+host+":"+port+"/"+database+"";
			ct = DriverManager.getConnection(url, user, pass);
			ps = ct.prepareStatement("select id  from canal where name = '"+name+"';");
			rs = ps.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id");
			}
			}
		catch (Exception cannalId) {
			cannalId.printStackTrace();
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
		return id;
	}

}
