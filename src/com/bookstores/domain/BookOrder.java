package com.bookstores.domain;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

public class BookOrder implements BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bookOrderID;
	private int bookUserID;
	private int addressID;
	private double amount;
	private int status;
	private String orderTime;
	private String payTime;

	public BookOrder() {
		this.bookOrderID = 0;
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		String current = simpleFormat.format(new Date());
		this.orderTime = current;
		this.payTime = current;
	}

	public void setBookOrderID(int bookOrderID) {
		this.bookOrderID = bookOrderID;
	}

	public int getBookOrderID() {
		return this.bookOrderID;
	}

	public void setBookUserID(int bookUserID) {
		this.bookUserID = bookUserID;
	}

	public int getBookUserID() {
		return this.bookUserID;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return this.status;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderTime() {
		return this.orderTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getPayTime() {
		return this.payTime;
	}
	
	public void setAddressID(int addressID){
		this.addressID = addressID;
	}
	
	public int getAddressID(){
		return this.addressID;
	}

	@Override
	public boolean equals(Object object) {
		if(object == null || this.getClass() != object.getClass()) return false;
		BookOrder that = (BookOrder) object;
		return this.bookOrderID == that.bookOrderID;
		
	}

	@Override
	public int hashCode() {
		return this.bookOrderID;
	}

}