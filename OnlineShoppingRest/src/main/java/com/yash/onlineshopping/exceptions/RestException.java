package com.yash.onlineshopping.exceptions;

public class RestException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private int messageCode; 
	private String message;
	
	public RestException() {
		
	}

	public RestException(int messageCode, String message) {
		super();
		this.messageCode = messageCode;
		this.message = message;
	}

	public int getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(int messageCode) {
		this.messageCode = messageCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
