package com.example.demo.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(Object id) {
        super("Object not found. Id: " + id);
    }
}
