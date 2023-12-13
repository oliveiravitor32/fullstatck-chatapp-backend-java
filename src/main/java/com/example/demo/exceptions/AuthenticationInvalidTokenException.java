package com.example.demo.exceptions;

public class AuthenticationInvalidTokenException extends RuntimeException{
    public AuthenticationInvalidTokenException(String msg) {
        super(msg);
    }
}
