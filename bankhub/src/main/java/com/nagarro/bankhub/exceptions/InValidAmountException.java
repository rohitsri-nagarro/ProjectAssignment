package com.nagarro.bankhub.exceptions;

import org.springframework.http.HttpStatus;

public class InValidAmountException extends GenericException {
	private static final long serialVersionUID = 1L;
	
	public InValidAmountException(String fieldName, String message) {
		super(fieldName, HttpStatus.BAD_REQUEST, message);
	}
}
