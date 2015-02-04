package com.bookstores.domain;

public class Category implements BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int categoryID;
	private String name;

	public Category() {
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public int getCategoryID() {
		return this.categoryID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public boolean equals(Object object) {
		if(object == null || this.getClass() != object.getClass()) return false;
		Category that = (Category) object;
		return this.categoryID == that.categoryID;
	}

	@Override
	public int hashCode() {
		return this.categoryID;
	}

}