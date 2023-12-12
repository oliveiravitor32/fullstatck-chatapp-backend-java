package com.example.demo.exceptions;

public class LikeNotFoundException extends RuntimeException{

    public LikeNotFoundException(String msg) {
        super(msg);
    }
}
