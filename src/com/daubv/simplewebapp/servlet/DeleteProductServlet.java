package com.daubv.simplewebapp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daubv.simplewebapp.utils.DBUtils;
import com.daubv.simplewebapp.utils.MyUtils;
import com.mysql.jdbc.Connection;

@WebServlet(urlPatterns = { "/deleteProduct" })
public class DeleteProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteProductServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = (Connection) MyUtils.getStoredConnection(req);
		String code = req.getParameter("code");
		String errorStr = null;

		try {
			DBUtils.deleteProduct(conn, code);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			errorStr = e.getMessage();
		}

		// Nếu có lỗi, forward (chuyển tiếp) sang trang thông báo lỗi.
		if (errorStr != null) {
			// Lưu thông tin vào request attribute trước khi forward sang views.
			req.setAttribute("errorString", errorStr);
			RequestDispatcher dispatcher = req.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/deleteProductErrorView.jsp");
			dispatcher.forward(req, resp);
		} else { // OK. Redirect chuyen huong sang trang DS san pham
			resp.sendRedirect(req.getContextPath() + "/productList");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
