package com.apicela.apicrypto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SaveException.class)
    public ResponseEntity<Object> handleSaveException(SaveException ex) {
        return ResponseEntity.status(HttpStatus.OK).body(ex.getMessage());
    }
}
