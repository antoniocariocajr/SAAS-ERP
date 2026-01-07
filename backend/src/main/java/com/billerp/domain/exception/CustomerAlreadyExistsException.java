package com.billerp.domain.exception;

public class CustomerAlreadyExistsException extends BusinessException {

    public CustomerAlreadyExistsException(String name) {
        super("Customer with name " + name + " already exists");
    }

}
