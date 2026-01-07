package com.billerp.domain.exception;

public class UserNotFoundException extends ResourceNotFoundException {

    private final String CODE = "USER_NOT_FOUND";
    private String FIELD = "id";

    public UserNotFoundException(String id) {
        super("User with id '%s' not found".formatted(id));
    }

    public UserNotFoundException(String value, String field) {
        super("User with " + field + " '%s' not found".formatted(value));
        this.FIELD = field;
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getField() {
        return FIELD;
    }
}
