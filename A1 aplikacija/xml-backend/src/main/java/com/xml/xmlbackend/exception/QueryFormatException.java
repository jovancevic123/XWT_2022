package com.xml.xmlbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class QueryFormatException extends RuntimeException {
    public QueryFormatException(String message) {
        super(message);
    }
}
