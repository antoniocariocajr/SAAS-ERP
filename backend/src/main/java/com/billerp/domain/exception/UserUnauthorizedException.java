package com.billerp.domain.exception;

public class UserUnauthorizedException extends BusinessException {

    public UserUnauthorizedException(String username) {
        super("User " + username + " is not authorized");
    }

    @Override
    public String getField() {
        return "password";
    }

    @Override
    public String getCode() {
        return "USER_UNAUTHORIZED";
    }
}
