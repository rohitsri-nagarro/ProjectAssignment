package com.nagarro.bankhub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.nagarro.bankhub.responsedto.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
   
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponseDto> genericExceptionHandler(GenericException genericException) {
        final String errorMessage = genericException.getMessage();
        final String fieldName = genericException.getFieldName();
        final HttpStatus errorCode = genericException.getHttpStatusCode();
        final ErrorResponseDto errorResponse = new ErrorResponseDto(fieldName,errorMessage);
        return new ResponseEntity<ErrorResponseDto>(errorResponse,errorCode );
    }
}
