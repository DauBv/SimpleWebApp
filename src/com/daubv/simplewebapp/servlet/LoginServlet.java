package com.daubv.simplewebapp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.daubv.simplewebapp.beans.UserAccount;
import com.daubv.simplewebapp.utils.DBUtils;
import com.daubv.simplewebapp.utils.MyUtils;
import com.mysql.jdbc.Connection;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		// TODO Auto-generated constructor stub
	}

	// show page login
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/**
		 * Forward tới trang /WEB-INF/views/loginView.jsp (Người dùng ko thể truy cập
		 * trực tiếp vào trang JSP đặt trong thư mục WEB-INF)
		 */
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
		dispatcher.forward(req, resp);
	}

	/**
	 * Khi nhập userName, password và nhấn Submit Phương thức này sẽ được thực thi
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName = req.getParameter("userName");
		String passWord = req.getParameter("password");
		String rememberMeStr = req.getParameter("rememberMe");
		boolean remember = "Y".equals(rememberMeStr);
		UserAccount user = null;
		boolean hasError = false;
		String errorString = "";
		if (userName == null || passWord == null || userName.length() == 0 || passWord.length() == 0) {
			hasError = true;
			errorString = "Required username and password!";
		} else {
			Connection conn = (Connection) MyUtils.getStoredConnection(req);
			try {
				System.out.println("nhay vao day");
				user = DBUtils.findUser(conn, userName, passWord);
				System.out.println("user user "+user);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				hasError = true;
				errorString = e.getMessage();
			}
		}

		// Trường hợp sảy ra lỗi forward (Chuyển hướng) tới /WEB-INF/views/login.jsp
		if (hasError) {
			user = new UserAccount(userName, passWord);
			// Lưu các thông tin vào request attribute trước khi forward.
			req.setAttribute("errorString", errorString);
			req.setAttribute("user", user);

			// Forward (Chuyển tiếp) tới trang /WEB-INF/views/login.jsp
			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/loginView.jsp");
			dispatcher.forward(req, resp);
		} else { // Truong hop ko co loi, luu thong tin vao Session, chuyen huong sang trang
					// userInfo
			HttpSession session = req.getSession();
			MyUtils.storeLoginedUser(session, user);

			// Neu nguoi dung chon tinh nang 'Remember me'
			// Luu thong tin nguoi dung vao Cookie
			if (remember) {
				MyUtils.storeUserCookie(resp, user);
			} else { // Nguoc lai xoa Cookie
				MyUtils.deleteUserCookie(resp);
			}
			// Redirect (Chuyển hướng) sang trang /userInfo.
			resp.sendRedirect(req.getContextPath() + "/userInfo");
		}
	}
}
