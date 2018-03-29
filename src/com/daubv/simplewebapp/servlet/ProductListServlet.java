package com.daubv.simplewebapp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daubv.simplewebapp.beans.Product;
import com.daubv.simplewebapp.utils.DBUtils;
import com.daubv.simplewebapp.utils.MyUtils;
import com.mysql.jdbc.Connection;

@WebServlet(urlPatterns = { "/productList" })
public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DEBUG = ProductListServlet.class.getSimpleName();

	public ProductListServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = (Connection) MyUtils.getStoredConnection(req);
		String errorString = "";
		List<Product> list = null;
		try {
			list = DBUtils.queryProduct(conn);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			errorString = e.getMessage();
		}
		System.out.println(DEBUG + list.get(0).getName());
		// Lưu thông tin vào request attribute trước khi forward sang views.
		req.setAttribute("errorString", errorString);
		req.setAttribute("productList", list);

		// Forward sang /WEB-INF/views/productListView.jsp
		RequestDispatcher dispatcher = req.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/productListView.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
