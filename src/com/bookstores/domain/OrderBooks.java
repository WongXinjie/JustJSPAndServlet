package com.bookstores.domain;

public class OrderBooks implements BaseDomain{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int orderBooksID;
	private int bookID;
	private int orderID;
	private int count;
	private String bookName;
	
	public OrderBooks(){}
	
	public void setOrderBooksID(int orderBooksID){
		this.orderBooksID = orderBooksID;
	}
	
	public int getOrderBooksID(){
		return this.orderBooksID;
	}
	
	public void setBookID(int bookID){
		this.bookID = bookID;
	}
	
	public int getBookID(){
		return this.bookID;
	}
	
	public void setOrderID(int orderID){
		this.orderID = orderID;
	}
	
	public int getOrderID(){
		return this.orderID;
	}
	
	public void setCount(int count){
		this.count = count;
	}
	
	public int getCount(){
		return this.count;
	}
	
	public void setBookName(String bookName){
		this.bookName = bookName;
	}
	
	public String getBookName(){
		return this.bookName;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object == null || this.getClass() != object.getClass()) return false;
		OrderBooks that = (OrderBooks) object;
		return this.bookID == that.bookID && this.orderID == that.orderID;
	}

	@Override
	public int hashCode() {
		final int hashMultiplier = 41;
		int result = 7;
		result = result * hashMultiplier + bookID;
		result = result * hashMultiplier + orderID;
		return result;
	}
}
