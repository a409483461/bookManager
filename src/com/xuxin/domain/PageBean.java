package com.xuxin.domain;

import java.util.List;

public class PageBean {
	private int currentPage;
	private int pagesize;
	private int count;
	private int totlalpage;
	private List books;
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTotlalpage() {
		return totlalpage;
	}
	public void setTolalpage(int totlalpage) {
		this.totlalpage = totlalpage;
	}
	public List getBooks() {
		return books;
	}
	public void setBooks(List books) {
		this.books = books;
	}
	
}
