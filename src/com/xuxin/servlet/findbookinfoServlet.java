package com.xuxin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xuxin.domain.Book;
import com.xuxin.service.BookServiceImpl;

public class findbookinfoServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		String id=request.getParameter("id");
		BookServiceImpl bs=new BookServiceImpl();
		Book book=bs.findBookById(id);
		request.setAttribute("b", book);
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doGet(request, response);
	}

}
