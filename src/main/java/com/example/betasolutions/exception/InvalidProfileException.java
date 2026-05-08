package com.example.betasolutions.exception;

public class InvalidProfileException extends RuntimeException {
    public InvalidProfileException(String message) {
        super(message);
    }
}
