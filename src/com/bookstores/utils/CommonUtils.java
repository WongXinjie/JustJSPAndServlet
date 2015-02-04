package com.bookstores.utils;
import java.util.regex.*;

public class CommonUtils {
	
	public static boolean isIneger(String str){
		Pattern pattern = Pattern.compile("^[\\d]*$");
		return pattern.matcher(str).matches();
	}
	
	public static boolean isDouble(String str){
		Pattern pattern = Pattern.compile("^[.\\d]*$");
		return pattern.matcher(str).matches();
	}
	
	public static boolean notEmpty(String... args){
		for(String str: args){
			str = str.trim();
			if(str.length()==0)
				return false;
		}
		return true;
	}
}
