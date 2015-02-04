package com.bookstores.utils;

public class UserValidUtil {
	public static boolean valid(String email, String name, String passwordA, String passwordB){
		email = email.trim();
		name = name.trim();
		passwordA = passwordA.trim();
		passwordB = passwordB.trim();
		if(email.equals("")||name.equals("")||passwordA.equals("")||passwordB.equals("")){
			return false;
		}else if(! passwordA.equals(passwordB)){
			return false;
		}else{
			return true;
		}
	}
}
