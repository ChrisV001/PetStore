package com.project.petstore.exceptions;

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
