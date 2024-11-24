package com.nagarro.bankhub.exceptions;

import org.springframework.http.HttpStatus;

public class InValidAccountNumberException extends GenericException{
	private static final long serialVersionUID = 1L;
	
    public InValidAccountNumberException(String fieldName, String message) {
        super(fieldName, HttpStatus.NOT_FOUND, message);
    }
}
