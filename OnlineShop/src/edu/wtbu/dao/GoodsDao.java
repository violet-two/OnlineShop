package edu.wtbu.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import edu.wtbu.helper.MySqlHelper;
import edu.wtbu.pojo.Goods;

public class GoodsDao {
	public static ArrayList<Goods> findGoods(){
		ArrayList<Goods> goodsList = new ArrayList<Goods>();
		String sql = "select * from goods";
		ResultSet rs = MySqlHelper.executeQuery(sql, null);
		try {
			while(rs.next()) {
				Goods goods = new Goods();
				goods.setId(rs.getInt(1));
				goods.setName(rs.getString(2));
				goods.setPrice(rs.getFloat(3));
				goods.setImgUrl(rs.getString(4));
				goods.setSellerAddress(rs.getString(5));
				goodsList.add(goods);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return goodsList;
	}
	public static Goods findGoodsById(int id){
		Goods goods = null;
		String sql = "select * from goods where id = ?";
		ResultSet rs = MySqlHelper.executeQuery(sql, new Object[] {id});
		try {
			while(rs.next()) {
				goods = new Goods();
				goods.setId(rs.getInt(1));
				goods.setName(rs.getString(2));
				goods.setPrice(rs.getFloat(3));
				goods.setImgUrl(rs.getString(4));
				goods.setSellerAddress(rs.getString(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return goods;
	}
	
	public static Goods findGoodsByName(String name){
		Goods goods = null;
		String sql = "select * from goods where name = ?";
		ResultSet rs = MySqlHelper.executeQuery(sql, new Object[] {name});
		try {
			while(rs.next()) {
				goods = new Goods();
				goods.setId(rs.getInt(1));
				goods.setName(rs.getString(2));
				goods.setPrice(rs.getFloat(3));
				goods.setImgUrl(rs.getString(4));
				goods.setSellerAddress(rs.getString(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return goods;
	}
}
