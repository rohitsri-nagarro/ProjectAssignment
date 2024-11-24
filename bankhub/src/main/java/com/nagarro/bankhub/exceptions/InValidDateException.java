package com.nagarro.bankhub.exceptions;

import org.springframework.http.HttpStatus;

public class InValidDateException extends GenericException {

	private static final long serialVersionUID = 1L;

	public InValidDateException(String fieldName, String message) {
		super(fieldName, HttpStatus.BAD_REQUEST, message);
	}
}
