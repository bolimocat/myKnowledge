/**
 * 验证源端数据库状态
 */
package com.autotestBridge.function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class checkSourceDB {
	
//	private String host;
//	private String user;
//	private String pass;
//	private String port;
//	
//	public checkSourceDB(String h,String u,String ps,String pt) {
//		host = h;
//		user = u;
//		pass = ps;
//		port = pt;
//	}
//	
	//定义数据库操作
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
	
	
	/**
	 * 验证mysql数据源的数据库状态
	 * @param host
	 * @param user
	 * @param pass
	 * @param port
	 * @return
	 */
	public boolean sourceMysqlStatus(String host,String user,String pass,String port) {
		boolean result = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+host+":"+port+"/mysql";
			ct = DriverManager.getConnection(url, user, pass);
			ps = ct.prepareStatement("select version();");
			ps.executeQuery();
			if(rs.next()) {
				result = true;
			}
			}
		catch (Exception ckSourceMySqlDB) {
			ckSourceMySqlDB.printStackTrace();
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
	 * 验证dbscale数据源的数据库状态
	 * @param host
	 * @param user
	 * @param pass
	 * @param port
	 * @return
	 */
		public boolean sourceDbscaleStatus(String host,String user,String pass,String port) {
			boolean result = false;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				String url = "jdbc:mysql://"+host+":"+port+"/mysql";
				ct = DriverManager.getConnection(url, user, pass);
				ps = ct.prepareStatement("select version();");
				ps.executeQuery();
				if(rs.next()) {
					result = true;
				}
				}
			catch (Exception ckSourceMySqlDB) {
				ckSourceMySqlDB.printStackTrace();
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
}
