package com.bookstores.exception;

public class EmailTakenException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EmailTakenException(){}
	public EmailTakenException(String message){
		super(message);
	}

}
