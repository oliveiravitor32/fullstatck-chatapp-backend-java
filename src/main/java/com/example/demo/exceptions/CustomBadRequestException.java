package com.example.demo.exceptions;

public class CustomBadRequestException extends RuntimeException{

    public CustomBadRequestException(String msg) {
        super(msg);
    }
}
