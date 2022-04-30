package edu.wtbu.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySqlHelper {
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/geth?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";
	private static String user = "root";
	private static String password = "123456";
	private static Connection conn = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;
	static {
		try {
			Class.forName(driver);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static int executeUpdate(String sql,Object[] param) {
		int result = 0;
		try {
			conn = DriverManager.getConnection(url,user,password);
			pstmt = conn.prepareStatement(sql);
			if(param!=null) {
				for (int i = 0; i < param.length; i++) {
					String className = param[i].getClass().getName();
					if(className.contains("String")) {
						pstmt.setString(i+1, param[i].toString());
					}else if(className.contains("Integer")) {
						pstmt.setInt(i+1, Integer.parseInt(param[i].toString()));
					}else if(className.contains("Float")) {
						pstmt.setFloat(i+1, Float.parseFloat(param[i].toString()));
					}
				}
			}
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			closeAll();
			
		}
		return result;
	}
	
	public static ResultSet executeQuery(String sql,Object[] param) {
		try {
			conn = DriverManager.getConnection(url,user,password);
			pstmt = conn.prepareStatement(sql);
			if(param!=null) {
				for (int i = 0; i < param.length; i++) {
					String className = param[i].getClass().getName();
					if(className.contains("String")) {
						pstmt.setString(i+1, param[i].toString());
					}else if(className.contains("Integer")) {
						pstmt.setInt(i+1, Integer.parseInt(param[i].toString()));
					}else if(className.contains("Float")) {
						pstmt.setFloat(i+1, Float.parseFloat(param[i].toString()));
					}
				}
			}
			rs = pstmt.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rs;
	}
	
	public static void closeAll() {
		if(conn!=null) {
			try {
				conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		if(rs!=null) {
			try {
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
