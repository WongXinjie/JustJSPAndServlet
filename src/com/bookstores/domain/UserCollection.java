package com.bookstores.domain;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

public class UserCollection implements BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int collectionID;
	private int bookUserID;
	private int bookID;
	private String addTime;

	public UserCollection() {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		this.addTime = simpleFormat.format(new Date());
	}
	
	public UserCollection(int bookUserID, int bookID){
		this.bookUserID = bookUserID;
		this.bookID = bookID;
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		this.addTime = simpleFormat.format(new Date());
	}

	public void setCollectionID(int collectionID) {
		this.collectionID = collectionID;
	}

	public int getCollectionID() {
		return this.collectionID;
	}

	public void setBookUserID(int bookUserID) {
		this.bookUserID = bookUserID;
	}

	public int getBookUserID() {
		return this.bookUserID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public int getBookID() {
		return this.bookID;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getAddTime() {
		return this.addTime;
	}

	@Override
	public boolean equals(Object object) {
		if(object == null || this.getClass() != object.getClass()) return false;
		UserCollection that = (UserCollection) object;
		return this.collectionID == that.collectionID;
	}

	@Override
	public int hashCode() {
		return this.collectionID;
	}

}