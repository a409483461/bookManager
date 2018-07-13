package com.xuxin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xuxin.domain.Book;
import com.xuxin.service.BookServiceImpl;

public class addCartServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取表单数据
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String id=request.getParameter("id");
		//调用业务逻辑
		BookServiceImpl bs=new BookServiceImpl();
		Book book=bs.findBookById(id);
		HttpSession session = request.getSession();
		Map<Book, String> cart=(Map<Book, String>) session.getAttribute("cart");
		int num=1;
		if(cart==null){
			cart=new HashMap<Book, String>();

		}
		if(cart.containsKey(book)){
			num=Integer.parseInt(cart.get(book))+1;
		}
		cart.put(book, num+"");
		session.setAttribute("cart", cart);
		//分发转向
		out.print("<a href='"+request.getContextPath()+"/servlet/pageServlet'>继续购买</a>,<a href='"+request.getContextPath()+"/cart.jsp'>查看购物车</a>");
		
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
		
	}

}
