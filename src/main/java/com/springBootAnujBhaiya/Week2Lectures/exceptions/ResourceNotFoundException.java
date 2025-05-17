package com.springBootAnujBhaiya.Week2Lectures.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    // constructor
    public ResourceNotFoundException(String message) {
        super(message);
    }


}
