package com.autotestBridge.function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class checkTargetDB {
	
	//定义数据库操作
			PreparedStatement ps = null;
			Connection ct = null;
			ResultSet rs = null;
	
	
	/**
	 * 验证目标mysql数据源的数据库状态
	 * @param host
	 * @param user
	 * @param pass
	 * @param port
	 * @return
	 */
	public boolean targetMysqlStatus(String host,String user,String pass,String port) {
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
		catch (Exception ckTargetMySqlDB) {
			ckTargetMySqlDB.printStackTrace();
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
	 * 验证目标dbscale数据源的数据库状态
	 * @param host
	 * @param user
	 * @param pass
	 * @param port
	 * @return
	 */
		public boolean targetDbscaleStatus(String host,String user,String pass,String port) {
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
			catch (Exception ckTargetDbscaleDB) {
				ckTargetDbscaleDB.printStackTrace();
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
		 * 验证目标为oracle的数据库状态
		 * @param host
		 * @param user
		 * @param pass
		 * @param port
		 * @return
		 */
		public boolean targetOracleStatus(String host,String user,String pass,String port) {
			boolean result = false;
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				String url = "jdbc:oracle:thin:@//"+host+":"+port+"/ORCLPDB1";
				ct = DriverManager.getConnection(url, user, pass);
//				ct = DriverManager.getConnection("jdbc:oracle:thin:@//172.16.110.69:1521/ORCLPDB1", "test", "test");
				ps = ct.prepareStatement("select version();");
				ps.executeQuery();
				if(rs.next()) {
					result = true;
				}
				}
			catch (Exception ckStargetOracleStatus) {
				ckStargetOracleStatus.printStackTrace();
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
