package com.yash.onlineshopping.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<RestException> handlerException() {
		RestException ex = new RestException(404, "Resource not found!");
		return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
	}
}
