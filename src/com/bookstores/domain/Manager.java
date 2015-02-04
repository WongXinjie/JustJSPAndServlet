package com.bookstores.domain;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

public class Manager implements BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int managerID;
	private String managerName;
	private String email;
	private int permission;
	private String createTime;
	private String lastLoginTime;
	private String password;
	
	public Manager(){
		this.permission = 0;
		SimpleDateFormat simpleFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		String current = simpleFormat.format(new Date());
		this.createTime = current;
		this.lastLoginTime = current;
	}

	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}

	public int getManagerID() {
		return this.managerID;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerName() {
		return this.managerName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public int getPermission() {
		return this.permission;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginTime() {
		return this.lastLoginTime;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object == null || this.getClass() != object.getClass()) return false;
		Manager that = (Manager) object;
		return this.managerID == that.managerID ;
	}

	@Override
	public int hashCode() {
		return this.managerID;
	}

}