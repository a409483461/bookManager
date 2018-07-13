package com.xuxin.service;

import java.sql.SQLException;
import java.util.List;

import com.xuxin.dao.BookDaoImpl;
import com.xuxin.domain.Book;
import com.xuxin.domain.PageBean;

public class BookServiceImpl {
	BookDaoImpl bookdao=new BookDaoImpl();
	public List<Book> findAllBooks(){
		try {
			return bookdao.findAllBooks();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void  addBook(Book book){
		try {
			bookdao.addBook(book);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Book findBookById(String id) {
		
		return bookdao.findBookById(id);
	}
	public void updateBook(Book book) throws SQLException{
		bookdao.updateBook(book);
	}
	public void delete(String id) throws SQLException{
		bookdao.delete(id);
	}
	public void delAllBooks(String[] ids) throws SQLException {
		// TODO Auto-generated method stub
		bookdao.delAllBooks(ids);
	}
	public List<Book> searchBooks(String id, String category, String name,
			String minprice, String maxprice) throws SQLException {
		return bookdao.searchBooks(id,category,name,minprice,maxprice);
		
	}
	public PageBean findBookpage(int currentPage, int pageSize) {
		int count;
		try {
			count = bookdao.count();
			int totalpage=(int) Math.ceil(count*1.0/pageSize);
			List<Book> books=bookdao.findBooks(currentPage, pageSize);
			PageBean pb=new PageBean();
			pb.setBooks(books);
			pb.setCount(count);
			pb.setCurrentPage(currentPage);
			pb.setPagesize(pageSize);
			pb.setTolalpage(totalpage);
			return pb;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据书名查找图书
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public List<Object> searchBookByName(String name) throws SQLException {
		// TODO Auto-generated method stub
		
		return bookdao.searchBookByName(name);
	}
	
}
