package com.example.demo.exceptions;

public class AuthenticationIncorrectLogin extends RuntimeException{

    public AuthenticationIncorrectLogin(String msg) {
        super(msg);
    }
}
