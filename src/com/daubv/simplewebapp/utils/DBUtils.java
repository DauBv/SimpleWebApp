package com.daubv.simplewebapp.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.daubv.simplewebapp.beans.Product;
import com.daubv.simplewebapp.beans.UserAccount;
import com.mysql.jdbc.PreparedStatement;

public class DBUtils {

	/**
	 * Method find User
	 * 
	 * @param conn
	 * @param userName
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public static UserAccount findUser(Connection conn, String userName, String password) throws SQLException {
		String sql = "Select a.USER_NAME, a.PASSWORD, a.GENDER from USER_ACCOUNT a "
				+ " where a.USER_NAME = ? and a.PASSWORD= ?";
		PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, password);
		ResultSet rs = pstm.executeQuery();
		System.out.println("anaahaah " + rs);
		System.out.println("aaaa");
		while (rs.next()) {
			UserAccount user = new UserAccount();
			user.setUserName(rs.getString(1));
			user.setPassword(rs.getString(2));
			user.setGender(rs.getString(3));
			return user;
		}
		return null;
	}

	/**
	 * Method find User
	 * 
	 * @param conn
	 * @param userName
	 * @return
	 * @throws SQLException
	 */
	public static UserAccount findUser(Connection conn, String userName) throws SQLException {
		String sql = "Select a.User_Name, a.Password, a.Gender from USER_ACCOUNT a "//
				+ " where a.User_Name = ? ";
		PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(sql);
		pstm.setString(1, userName);

		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(rs.getString("Password"));
			user.setGender(rs.getString("Gender"));
			return user;
		}
		return null;
	}

	/**
	 * Method queryProduct
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public static List<Product> queryProduct(Connection conn) throws SQLException {
		String sql = "Select a.Code, a.Name, a.Price from PRODUCT a ";
		PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		List<Product> list = new ArrayList<Product>();
		while (rs.next()) {
			Product product = new Product();
			product.setCode(rs.getString("Code"));
			product.setName(rs.getString("Name"));
			product.setPrice(rs.getFloat("Price"));
			list.add(product);
		}
		System.out.println("list product size " + list.size());
		return list;
	}

	/**
	 * Method findProduct
	 * 
	 * @param conn
	 * @param code
	 * @throws SQLException
	 */
	public static Product findProduct(Connection conn, String code) throws SQLException {
		String sql = "Select a.Code, a.Name, a.Price from PRODUCT a where a.Code=?";
		PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(sql);
		pstm.setString(1, code);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			String name = rs.getString("Name");
			float price = rs.getFloat("Price");
			Product product = new Product(code, name, price);
			return product;
		}
		return null;
	}

	/**
	 * Method update product
	 * 
	 * @param conn
	 * @param product
	 * @throws SQLException
	 */
	public static void updateProduct(Connection conn, Product product) throws SQLException {
		String sql = "Update PRODUCT set Name =?, Price=? where Code=? ";
		PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(sql);
		pstm.setString(1, product.getName());
		pstm.setFloat(2, product.getPrice());
		pstm.setString(3, product.getCode());
		pstm.executeUpdate();
	}

	/**
	 * Method insert Product
	 * 
	 * @param conn
	 * @param product
	 * @throws SQLException
	 */
	public static void insertProduct(Connection conn, Product product) throws SQLException {
		String sql = "Insert into PRODUCT(Code, Name,Price) values (?,?,?)";
		PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(sql);
		pstm.setString(1, product.getCode());
		pstm.setString(2, product.getName());
		pstm.setFloat(3, product.getPrice());
		pstm.executeUpdate();
	}

	public static void deleteProduct(Connection conn, String code) throws SQLException {
		String sql = "Delete From PRODUCT where Code= ?";
		PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(sql);
		pstm.setString(1, code);
		pstm.executeUpdate();
	}
}
