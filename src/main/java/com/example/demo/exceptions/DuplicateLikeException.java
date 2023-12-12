package com.example.demo.exceptions;

public class DuplicateLikeException extends RuntimeException{

    public DuplicateLikeException(String msg) {
        super(msg);
    }
}
