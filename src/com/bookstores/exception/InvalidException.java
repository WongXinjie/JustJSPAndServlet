package com.bookstores.exception;

public class InvalidException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InvalidException(){}
	public InvalidException(String message){
		super(message);
	}
}
