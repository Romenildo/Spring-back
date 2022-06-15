package com.Ktr.helpdesk.services.exceptions;

public class DataIngegrityViolationException extends RuntimeException {

    public DataIngegrityViolationException(String message) {
        super(message);
    }

    public DataIngegrityViolationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
