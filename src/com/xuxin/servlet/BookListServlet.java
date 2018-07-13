package com.xuxin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xuxin.domain.Book;
import com.xuxin.service.BookServiceImpl;

public class BookListServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		//逻辑业务
		BookServiceImpl bsi=new BookServiceImpl();
		List<Book> list=bsi.findAllBooks();
		//分发转向
		
			request.setAttribute("books", list);
			request.getRequestDispatcher("/admin/products/list.jsp").forward(request, response);
			
	
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
