package com.xml.backend.p1.exceptions;

public class OperationFailedException extends RuntimeException{

    public OperationFailedException(String message) {
        super(message);
    }
}
