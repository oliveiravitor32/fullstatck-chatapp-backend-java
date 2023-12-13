package com.example.demo.exceptions;

public class AuthenticationUserAlreadyExistsException extends RuntimeException{

    public AuthenticationUserAlreadyExistsException(String msg) {
        super(msg);
    }
}
