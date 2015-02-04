package com.bookstores.exception;

public class InsertFailException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InsertFailException(){}
	public InsertFailException(String message){
		super(message);
	}
}
