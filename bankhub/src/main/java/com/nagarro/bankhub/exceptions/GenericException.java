package com.nagarro.bankhub.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class GenericException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private String fieldName;
    private HttpStatus httpStatusCode;

    public GenericException(final String fieldName,final HttpStatus httpStatusCode,final String message) {
        super(message);
        this.fieldName = fieldName;
        this.httpStatusCode = httpStatusCode;
    }
}
