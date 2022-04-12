//用例中间转化等相关内容
package com.autotestBridge.testcase.function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.autotestBridge.testcase.dom.caseTestCaseBean;

public class caseTransferTool {
	
	//根据从testcase表中查到的当前masterslave相关步骤中用到的masterslave表的id，
	//从该id得到的在masterslave的master_info（数据源id）信息
	
	//定义数据库操作
			PreparedStatement ps = null;
			Connection ct = null;
			ResultSet rs = null;
			
			private String host;
			private String port;
			private String user;
			private String pass;
			private String database;
	
	public caseTransferTool(String h,String u,String p,String pt,String db) {
		host = h;
		port = pt;
		user = u;
		pass = p;
		database = db;
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 从masterslave获得master_info
	 * @param masterslaveID
	 * @return
	 */
	public String fetchMasterinfo(int masterslaveID) {
		String source_datasource = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+host+":"+port+"/"+database+"";
			ct = DriverManager.getConnection(url, user, pass);
			ps = ct.prepareStatement("select master_info from masterslave where id = '+masterslaveID+'");
			rs = ps.executeQuery();
			if(rs.next()) {
				source_datasource = rs.getString("master_info");
			}
			}
		catch (Exception fetchMasterinfo) {
			fetchMasterinfo.printStackTrace();
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
		return source_datasource;
	}

}
