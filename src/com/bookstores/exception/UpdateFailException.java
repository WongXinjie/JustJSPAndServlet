package com.bookstores.exception;

public class UpdateFailException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UpdateFailException(){}
	public UpdateFailException(String message){
		super(message);
	}
}
