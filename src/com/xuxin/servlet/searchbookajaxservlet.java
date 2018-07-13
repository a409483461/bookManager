package com.xuxin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xuxin.domain.Book;
import com.xuxin.service.BookServiceImpl;

public class searchbookajaxservlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");//只能解决post方式的乱码
		response.setContentType("text/html;charset=UTF-8");
		String name=request.getParameter("name");
		
		BookServiceImpl bs=new BookServiceImpl();
		try {
			List<Object> list=bs.searchBookByName(name);
			String str = "";
			for (int i = 0; i < list.size(); i++) {
				if(i>0){
					str+=list.get(i)+",";
				}
				str+=list.get(i);
			}
			//request.setAttribute("str", str);
			//request.getRequestDispatcher("/cart.jsp").forward(request, response);
			//
			System.out.println(str);
			response.getWriter().write(str);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
