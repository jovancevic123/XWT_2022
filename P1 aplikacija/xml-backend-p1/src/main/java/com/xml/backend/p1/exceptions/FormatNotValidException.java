package com.xml.backend.p1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormatNotValidException extends RuntimeException{

    public FormatNotValidException() {
        super("Xml document is not valid!");
    }
}
