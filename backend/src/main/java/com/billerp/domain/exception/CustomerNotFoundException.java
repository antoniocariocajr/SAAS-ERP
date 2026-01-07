package com.billerp.domain.exception;

public class CustomerNotFoundException extends BusinessException {

    public CustomerNotFoundException(String parameter) {
        super("Customer solicited " + parameter + " not found");
    }

}
