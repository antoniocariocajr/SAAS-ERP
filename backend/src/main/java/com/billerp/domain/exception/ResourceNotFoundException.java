package com.billerp.domain.exception;

public abstract class ResourceNotFoundException extends RuntimeException {

    public abstract String getField();

    public abstract String getCode();

    public ResourceNotFoundException(String message) {
        super(message);
    }
}