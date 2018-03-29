package com.daubv.simplewebapp.utils;

import java.sql.Connection;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.daubv.simplewebapp.beans.UserAccount;

public class MyUtils {
	public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
	private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";

	/**
	 * Lưu trữ Connection vào attribute của request. Thông tin lưu trữ này chỉ tồn
	 * tại trong thời gian yêu cầu (request) cho tới khi dữ liệu được trả về trình
	 * duyệt người dùng.
	 * 
	 * @param request
	 * @param conn
	 */
	public static void storeConnection(ServletRequest request, Connection conn) {
		request.setAttribute(ATT_NAME_CONNECTION, conn);
	}

	/**
	 * Lấy đối tượng Connection đã được lưu trữ trong attribute của request.
	 * 
	 * @param request
	 * @return
	 */
	public static Connection getStoredConnection(ServletRequest request) {
		return (Connection) request.getAttribute(ATT_NAME_CONNECTION);
	}

	/**
	 * Lưu trữ thông tin người dùng đã login vào Session
	 * 
	 * @param session
	 * @param loginedUser
	 */
	public static void storeLoginedUser(HttpSession session, UserAccount loginedUser) {
		// Trên JSP có thể truy cập thông qua ${loginedUser}
		session.setAttribute("loginedUser", loginedUser);
	}

	/**
	 * Lấy thông tin người dùng lưu trữ trong Session.
	 * 
	 * @param session
	 * @return
	 */
	public static UserAccount getLoginedUser(HttpSession session) {
		return (UserAccount) session.getAttribute("loginedUser");
	}

	/**
	 * Lưu thông tin người dùng vào Cookie.
	 * 
	 * @param response
	 * @param user
	 */
	public static void storeUserCookie(HttpServletResponse response, UserAccount user) {
		System.out.println("Store user cookie");
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getUserName());
		// 1 ngày (Đã đổi ra giây)
		cookieUserName.setMaxAge(24 * 60 * 60);
		response.addCookie(cookieUserName);
	}

	/**
	 * Method Lấy thông tin người dùng trong Cookie
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserNameInCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (ATT_NAME_USER_NAME.equals(cookie.getName()))
					return cookie.getValue();
			}
		}
		return null;
	}
	
	/**
	 * Method Xóa Cookie của người dùng
	 * @param response
	 */
	public static void deleteUserCookie(HttpServletResponse response) {
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
		cookieUserName.setMaxAge(0);
		response.addCookie(cookieUserName);
	}
}
