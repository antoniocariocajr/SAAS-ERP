package com.billerp.domain.exception;

public class CustomerAlreadyExistsException extends BusinessException {
    private String field;

    public CustomerAlreadyExistsException(String name) {
        super("Customer with name " + name + " already exists");
    }

    public CustomerAlreadyExistsException(String value, String field) {
        super("Customer with " + field + " '%s' already exists".formatted(value));
        this.field = field;
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public String getCode() {
        return "CUSTOMER_ALREADY_EXISTS";
    }
}
