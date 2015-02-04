package com.bookstores.exception;

public class EmptyException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyException(){
	}
	
	public EmptyException(String message){
		super(message);
	}

}
