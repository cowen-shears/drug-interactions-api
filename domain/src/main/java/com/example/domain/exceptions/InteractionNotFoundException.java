package com.example.domain.exceptions;

public class InteractionNotFoundException extends RuntimeException {
    public InteractionNotFoundException(String message) {
        super(message);
    }
}
