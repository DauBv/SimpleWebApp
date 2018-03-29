package com.daubv.simplewebapp.servlet;

import java.io.IOException;

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

@WebServlet(urlPatterns = { "/editProduct" })
public class EditProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditProductServlet() {
		// TODO Auto-generated constructor stub
	}

	// Hien thi trang sua san pham
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = (Connection) MyUtils.getStoredConnection(req);
		String code = (String) req.getParameter("code");

		Product product = null;
		String errorStr = null;
		try {
			product = DBUtils.findProduct(conn, code);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			errorStr = e.getMessage();
		}

		/**
		 * Ko co loi, san pham ko ton tai de Edit, redirect sang trang ds san pham
		 */
		if (errorStr != null && product == null) {
			resp.sendRedirect(req.getServletPath() + "/productList");
			return;
		}

		// Lưu thông tin vào request attribute trước khi forward sang views.
		req.setAttribute("errorString", errorStr);
		req.setAttribute("product", product);
		RequestDispatcher dispatcher = req.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/editProductView.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = (Connection) MyUtils.getStoredConnection(req);
		String code = req.getParameter("code");
		String name = req.getParameter("name");
		String priceStr = req.getParameter("price");
		float price = 0;
		try {
			price = Float.parseFloat(priceStr);
		} catch (Exception e) {
		}
		Product product = new Product(code, name, price);
		String errorString = null;
		
		try {
			DBUtils.updateProduct(conn, product);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			errorString = e.getMessage();
		}
		
		// Lưu thông tin vào request attribute trước khi forward sang views.
		req.setAttribute("errorString", errorString);
		req.setAttribute("product", product);
		// Nếu có lỗi forward sang trang edit.
		if(errorString != null) {
			RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/editProductView.jsp");
			dispatcher.forward(req, resp);
		} else { //OK. Redirect sang trang Ds san pham
			resp.sendRedirect(req.getContextPath() + "/productList");
		}
	}
}
