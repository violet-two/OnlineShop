package edu.wtbu.dao;

import java.sql.ResultSet;

import edu.wtbu.helper.MySqlHelper;
import edu.wtbu.pojo.User;

public class UserDao {
	public static User findEmailAndPassword(String email,String password) {
		User user = null;
		String sql = "select * from user where email = ? and password = ?";
		ResultSet rs = MySqlHelper.executeQuery(sql, new Object[] {email,password});
		try {
			while(rs.next()) {
				user = new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setPassword(rs.getString(4));
				user.setAddress(rs.getString(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return user;
	}
	public static User findEmail(String email) {
		User user = null;
		String sql = "select * from user where email = ?";
		ResultSet rs = MySqlHelper.executeQuery(sql, new Object[] {email});
		try {
			while(rs.next()) {
				user = new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setPassword(rs.getString(4));
				user.setAddress(rs.getString(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return user;
	}
	
	public static int addUser(String name,String email,String password,String address) {
		int result = 0;
		String sql = "insert into user (name,email,password,address) values (?,?,?,?)";
		result = MySqlHelper.executeUpdate(sql, new Object[] {name,email,password,address});
		return result;
	}
}
