package com.billerp.domain.exception;

public class UserException extends BusinessException {
    private String field;

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, String field) {
        super(message);
        this.field = field;
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public String getCode() {
        return "USER_EXCEPTION";
    }
}
