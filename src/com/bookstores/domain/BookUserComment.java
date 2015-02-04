package com.bookstores.domain;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

public class BookUserComment implements BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bookUserCommentID;
	private int bookUserID;
	private int bookID;
	private String content;
	private String commentTime;
	private String updateTime;

	public BookUserComment() {
		this.bookUserCommentID = 0;
		SimpleDateFormat  simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		String current = simpleFormat.format(new Date());
		this.commentTime = current;
		this.updateTime = current;
	}
	
	public void setBookUserCommentID(int bookUserCommentID) {
		this.bookUserCommentID = bookUserCommentID;
	}

	public int getBookUserCommentID() {
		return this.bookUserCommentID;
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

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public String getCommentTime() {
		return this.commentTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

	@Override
	public boolean equals(Object object) {
		if(object == null || this.getClass() != object.getClass()) return false;
		BookUserComment that = (BookUserComment) object;
		return this.bookUserCommentID ==  that.bookUserCommentID;
	}

	@Override
	public int hashCode() {
		return this.bookUserCommentID;
	}

}