package com.xml.xmlbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED, reason = "Xml document is not valid!")
public class FormatNotValidException extends RuntimeException {
    public FormatNotValidException() {
        super("Xml document is not valid!");
    }
}
