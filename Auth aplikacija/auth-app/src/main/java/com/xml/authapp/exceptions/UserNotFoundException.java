package com.xml.authapp.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
        super("User is not registrated!");
    }
}
