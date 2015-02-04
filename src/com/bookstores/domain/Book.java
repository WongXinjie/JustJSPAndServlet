package com.bookstores.domain;

public class Book implements BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int bookID;
	private String title;
	private String ISBN;
	private String publisher;
	private String publishedDate;
	private String cover;
	private String description;
	private double price;
	private int categroyID;

	public Book(){
		this.bookID = 0;
		this.cover = "";
	}
	
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public int getBookID() {
		return this.bookID;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public String getISBN() {
		return this.ISBN;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublisher() {
		return this.publisher;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrice() {
		return this.price;
	}

	public void setCategroyID(int categroyID) {
		this.categroyID = categroyID;
	}

	public int getCategroyID() {
		return this.categroyID;
	}
	
	public void setPublishedDate(String publishedDate){
		this.publishedDate = publishedDate;
	}
	
	public String getPublishedDate(){
		return this.publishedDate;
	}
	
	public void setCover(String cover){
		this.cover = cover;
	}
	
	public String getCover(){
		return this.cover;
	}

	@Override
	public boolean equals(Object object) {
		if(object == null || this.getClass() != object.getClass()) return false;
		Book that = (Book) object;
		return this.ISBN.equals(that.ISBN);
	}

	@Override
	public int hashCode() {
		final int hashMultiplier = 41;
		int result = 7;
		result = result * hashMultiplier + ISBN.hashCode();
		return result;
	}

}