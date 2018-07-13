package com.xuxin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xuxin.domain.Book;
import com.xuxin.service.BookServiceImpl;

public class changeNum extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String id = request.getParameter("id");
		String num = request.getParameter("num");
		BookServiceImpl bs=new BookServiceImpl();
		Book book = bs.findBookById(id);
		
		HttpSession session = request.getSession();
		Map<Book, String> cart = (Map<Book, String>) session.getAttribute("cart");
		System.out.println(cart==null);
		/*Book b=new Book();
		b.setId(id);*/
		System.out.println(book);
		System.out.println(cart.containsKey(book)+"-----------");
		if("0".equals(num)){
			cart.remove(book);
			
		}
		if(cart.containsKey(book)){
			cart.put(book, num);
		}
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
		
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
