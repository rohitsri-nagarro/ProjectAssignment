package com.nagarro.bankhub.exceptions;

import org.springframework.http.HttpStatus;

public class DateParsingException extends GenericException {
	private static final long serialVersionUID = 1L;

	public DateParsingException(String fieldName, String message) {
		super(fieldName, HttpStatus.NOT_FOUND, message);
	}

}