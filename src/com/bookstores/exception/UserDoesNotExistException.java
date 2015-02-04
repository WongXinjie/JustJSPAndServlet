package com.bookstores.exception;

public class UserDoesNotExistException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UserDoesNotExistException(){}
	public UserDoesNotExistException(String message){
		super(message);
	}
}
