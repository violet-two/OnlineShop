package edu.wtbu.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import edu.wtbu.helper.MySqlHelper;
import edu.wtbu.pojo.Order;

public class OrderDao {
	public static int addOrder(String imgUrl,String goodsName,float orderPrice,String buyTime,int status,String transactionHash,String userEmail) {
		int result = 0;
		String sql = "insert into order_info (img_url,goods_name,order_price,buy_time,status,transaction_hash,user_email) values (?,?,?,?,?,?,?)";
		result = MySqlHelper.executeUpdate(sql, new Object[] {imgUrl,goodsName,orderPrice,buyTime,status,transactionHash,userEmail});
	    return result;
	}
	public static int updatePayOrder(String payTime,int status,String orderAddress,int id) {
		int result = 0;
		String sql = "update order_info set pay_time = ?,status = ?, order_address = ? where id= ?";
		result = MySqlHelper.executeUpdate(sql, new Object[] {payTime,status,orderAddress,id});
		return result;
	}
	public static ArrayList<Order> findOrderByEmail(String email){
		ArrayList<Order> list = new ArrayList<Order>();
		String sql = "select * from order_info where user_email = ?";
		ResultSet rs = MySqlHelper.executeQuery(sql, new Object[] {email});
		try {
			while(rs.next()) {
				Order order = new Order();
				order.setId(rs.getInt(1));
				order.setImgUrl(rs.getString(2));
				order.setGoodsName(rs.getString(3));
				order.setOrderPrice(rs.getFloat(4));
				order.setBuyTime(rs.getString(5));
				order.setPayTime(rs.getString(6));
				order.setStatus(rs.getInt(7));
				order.setTransactiomHash(rs.getString(8));
				order.setOrderAddress(rs.getString(9));
				order.setUserEmail(rs.getString(10));
				list.add(order);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	public static Order findOrderById(int id){
		Order order =null;
		String sql = "select * from order_info where id = ?";
		ResultSet rs = MySqlHelper.executeQuery(sql, new Object[] {id});
		try {
			while(rs.next()) {
				order = new Order();
				order.setId(rs.getInt(1));
				order.setImgUrl(rs.getString(2));
				order.setGoodsName(rs.getString(3));
				order.setOrderPrice(rs.getFloat(4));
				order.setBuyTime(rs.getString(5));
				order.setPayTime(rs.getString(6));
				order.setStatus(rs.getInt(7));
				order.setTransactiomHash(rs.getString(8));
				order.setOrderAddress(rs.getString(9));
				order.setUserEmail(rs.getString(10));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return order;
	}
}
