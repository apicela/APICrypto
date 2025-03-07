package com.apicela.apicrypto.exceptions;

public class UpdateException extends RuntimeException {
    public UpdateException(String message) {
        super(message);
    }

    public UpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}