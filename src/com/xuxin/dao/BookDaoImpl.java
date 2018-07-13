package com.xuxin.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.xuxin.domain.Book;
import com.xuxin.domain.PageBean;
import com.xuxin.utils.C3P0Util;

public class BookDaoImpl {
	public List<Book> findAllBooks() throws SQLException{
		
		QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select* from book", new BeanListHandler<Book>(Book.class));
		
	}
	
	public void addBook(Book book) throws SQLException{
		QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
		qr.update("insert into book values(?,?,?,?,?,?,?)",book.getId(),book.getName(),book.getPrice(),book.getCategory(),book.getDescription(),book.getPnum(),book.getImg_url() );
	
	}

	public Book findBookById(String id) {
		QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());

		try {
			return qr.query("select * from book where id=?",new BeanHandler<Book>(Book.class),id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @param book
	 * @return 
	 * @throws SQLException
	 */
	public void updateBook(Book book) throws SQLException{
		QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
		qr.update("update book set name=?,price=?,pnum=?,category=?,description=? where id=?",book.getName(),book.getPrice(),book.getPnum(),book.getCategory(),book.getDescription(),book.getId());
		
	}

	public void delete(String id) throws SQLException {
		QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
		qr.update("delete from book where id=?",id);
		
	}
/**
 * 批量删除
 * @param ids
 * @throws SQLException 
 */
	public void delAllBooks(String[] ids) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
		Object[][] params=new Object[ids.length][];
		for(int i=0;i<params.length;i++){
			params[i]=new Object[]{ids[i]};//循环给每一个一维数组中的元素赋值，值是id；
		}
			
		//???多看多背
		qr.batch("delete from book where id=?",params);
	}

public List<Book> searchBooks(String id, String category, String name,
		String minprice, String maxprice) throws SQLException {
	QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
	String sql="select *from book where 1=1";
	if(!"".equals(id.trim())){
		sql+=" and id like "+"'%"+id+"%'";
	}
	if(!"".equals(category.trim())){
		sql+=" and category like "+"'%"+category+"%'";
	}
	if(!"".equals(name.trim())){
		sql+=" and name like "+"'%"+name+"%'";
	}
	
	if(!"".equals(minprice.trim())){
		sql+=" and price > "+minprice;
	}
	
	if(!"".equals(maxprice.trim())){
		sql+=" and price < "+maxprice;
	}
	System.out.println(sql);
	return qr.query(sql, new BeanListHandler<Book>(Book.class));
	 
}

public List<Book> findBooks(int currentPage, int pageSize) throws SQLException {
	QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
	List<Book> books=qr.query("select * from book limit ?,?",new BeanListHandler<Book>(Book.class),(currentPage-1)*pageSize,pageSize);
	return books;
}

public int count() throws SQLException {
	QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
	long l=(Long)qr.query("select count(*) from book", new ScalarHandler(1));
	int count=(int)l;
	return count;
}

public List<Object> searchBookByName(String name) throws SQLException {
	// TODO Auto-generated method stub
	QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
	return qr.query("select name from book where name like ?", new ColumnListHandler(),"%"+name+"%");
	
}
	
	
	
}
