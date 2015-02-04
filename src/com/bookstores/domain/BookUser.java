package com.bookstores.domain;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

public class BookUser implements BaseDomain{
	
	private int bookUserID;
	private String bookUserName;
	private String email;
	private String password;
	private int addressID;
	private String registeredTime;
	private String lastLoginTime;
	
	private static final long serialVersionUID = 1L;
	public BookUser(){
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		String current = simpleFormat.format(new Date());
		this.registeredTime = current;
		this.lastLoginTime = current;
	}
	
	public void setBookUserID(int bookUserID){
		this.bookUserID = bookUserID;
	}
	
	public int getBookUserID(){
		return this.bookUserID;
	}
	
	public void setBookUserName(String bookUserName){
		this.bookUserName = bookUserName;
	}
	
	public String getBookUserName(){
		return this.bookUserName;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setAddressID(int addressID){
		this.addressID = addressID;
	}
	
	public int getAddressID(){
		return this.addressID;
	}
	
	public void setRegisteredTime(String registeredTime){
		this.registeredTime = registeredTime;
	}
	
	public String getRegisteredTime(){
		return this.registeredTime;
	}
	
	public void setLasLoginTime(String lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}
	
	public String getLastLoginTime(){
		return this.lastLoginTime;
	}
	
	@Override
	public boolean equals(Object object){
		if(object == null) return false;
		if(this.getClass() != object.getClass()) return false;
		BookUser that = (BookUser) object;
		return this.bookUserID == that.bookUserID && this.email.equals(that.email);
	}
	
	@Override
	public int hashCode(){
		final int hashMultiplier = 41;
		int result = 7;
		result = result * hashMultiplier + (new Integer(bookUserID)).hashCode();
		result = result * hashMultiplier + email.hashCode();
		return result;
	}
	
	
	
}
