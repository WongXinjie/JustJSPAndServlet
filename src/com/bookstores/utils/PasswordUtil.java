package com.bookstores.utils;

import java.security.MessageDigest;

public class PasswordUtil{
	
	private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6",
		"7", "8", "9", "a", "b", "c", "d", "e", "f"};
	
	public static String createPasswordHash(String email, String password){
		String original = password.concat(email);
		return encodeByMD5(original);
	}
	
	public static boolean validatePassword(String email, String password, String passwordHash){
		String inputPasswordHash = encodeByMD5(password.concat(email));
		if(passwordHash.equals(inputPasswordHash)){
			return true;
		}else{
			return false;
		}
	}
	
	private static String encodeByMD5(String password){
		if(password != null){
			try{
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] pwBytes = md.digest(password.getBytes());
				String passwordHash = convertByteArrayToString(pwBytes);
				return passwordHash;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private static String convertByteArrayToString(byte[] array){
		StringBuffer buffer = new StringBuffer();
		for(byte b: array){
			buffer.append(convertByteToString(b));
		}
		return buffer.toString();
	}
	
	private static String convertByteToString(byte b){
		int n = b;
		if( n < 0) n += 256;
		int rightDigit = n % 16;
		int leftDigit = n/ 16;
		
		return hexDigits[leftDigit].concat(hexDigits[rightDigit]);
	}
}
