package com.yash.onlineshopping.util;

import java.util.Date;

public class ErrorDetails extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private int messageCode;
	private String message, messageDesc;
	private Date date;

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

	public String getMessageDesc() {
		return messageDesc;
	}

	public void setMessageDesc(String messageDesc) {
		this.messageDesc = messageDesc;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ErrorDetails(int messageCode, String message, String messageDesc, Date date) {
		super();
		this.messageCode = messageCode;
		this.message = message;
		this.messageDesc = messageDesc;
		this.date = date;
	}

	public ErrorDetails() {
		// TODO Auto-generated constructor stub
	}

}
