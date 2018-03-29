package com.daubv.simplewebapp.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnUtils {
	
	/**
	 * Method get MySQLConnection 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
		String hostName = "localhost";
		String dbName = "dbTest";
		String userName = "root";
		String password = "daubv@2017";
		return getMySQLConnection(hostName, dbName, userName, password);
	}
	
	/**
	 * Method get MySQLConnection
	 * @param hostName
	 * @param dbName
	 * @param userName
	 * @param password
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password)
			throws ClassNotFoundException, SQLException {
		 Class.forName("com.mysql.jdbc.Driver");
	     String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
	     return DriverManager.getConnection(connectionURL, userName, password);
	}
}
