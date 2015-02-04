package com.bookstores.domain;

public class Address implements BaseDomain {

	private static final long serialVersionUID = 1L;
	private int addressID;
	private String country;
	private String province;
	private String city;
	private String district;
	private String street;
	private String zip;

	public Address() {
	}

	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}

	public int getAddressID() {
		return this.addressID;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return this.country;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvince() {
		return this.province;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return this.city;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDistrict() {
		return this.district;
	}
	
	public void setStreet(String street){
		this.street = street;
	}
	
	public String getStreet(){
		return this.street;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getZip() {
		return this.zip;
	}

	@Override
	public boolean equals(Object object) {
		if(object == null || this.getClass() != object.getClass()) return false;
		Address that = (Address)object;
		return this.addressID == that.addressID;
	}

	@Override
	public int hashCode() {
		return addressID;
	}

}