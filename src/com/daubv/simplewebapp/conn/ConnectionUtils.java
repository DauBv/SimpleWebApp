package com.daubv.simplewebapp.conn;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {
	
	/**
	 * Method get Connection
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		return MySQLConnUtils.getMySQLConnection();
	}
	
	/**
	 * Method close Quietly
	 * @param conn
	 */
	public static void closeQuietly(Connection conn) {
		try {
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * Method Connection rollback
	 * @param conn
	 */
	public static void rollbackQuietly(Connection conn) {
		try {
			conn.rollback();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
