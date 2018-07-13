package com.xuxin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xuxin.domain.PageBean;
import com.xuxin.service.BookServiceImpl;

public class PageServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		int PageSize=4;
		int currentPage=1;//µ±Ç°Ò³
		
		String currPage=request.getParameter("currentPage");
		if(currPage!=null){
			currentPage=Integer.parseInt(currPage);
		}
		BookServiceImpl bs=new BookServiceImpl();
		PageBean pb=bs.findBookpage(currentPage,PageSize);
		request.setAttribute("pb", pb);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
		
	}

}
