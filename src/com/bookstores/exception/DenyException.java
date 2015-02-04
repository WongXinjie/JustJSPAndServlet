package com.bookstores.exception;

public class DenyException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DenyException(){}
	public DenyException(String message){
		super(message);
	}

}
