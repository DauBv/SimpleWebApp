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

@WebServlet(urlPatterns = { "/createProduct" })
public class CreateProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DEBUG = CreateProductServlet.class.getSimpleName();

	public CreateProductServlet() {
		// TODO Auto-generated constructor stub
	}

	// Hiển thị trang tạo sản phẩm
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = req.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/createProductView.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Lấy đối tượng Connection đã được lưu trữ trong attribute của request.
		Connection conn = (Connection) MyUtils.getStoredConnection(req);
		String code = req.getParameter("code");
		String name = req.getParameter("name");
		String priceStr = req.getParameter("price");
		float price = 0;
		try {
			price = Float.parseFloat(priceStr);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Product product = new Product(code, name, price);
		String errorString = null;

		// Mã sản phẩm phải là chuỗi chữ [a-zA-Z_0-9] va Có ít nhất một ký tự
		System.out.println(DEBUG + " CODE " +code);
		String regex = "\\w+";
		if (code == null || !code.matches(regex)) {
			errorString = "Product Code invalid!";
		}
		if (errorString == null) {
			try {
				DBUtils.insertProduct(conn, product);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				errorString = e.getMessage();
			}
		}
		// Lưu thông tin vào request attribute trước khi forward sang views.
		req.setAttribute("errorString", errorString);
		req.setAttribute("product", product);

		// Nếu có lỗi forward (chuyển tiếp) sang trang
		if (errorString != null) {
			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/createProductView.jsp");
			dispatcher.forward(req, resp);
		} else { // Moi thu OK, Chuyen huong sang trang danh sach san pham
			resp.sendRedirect(req.getContextPath() + "/productList");
		}
	}
}
