package com.billerp.domain.exception;

public class CustomerNotFoundException extends ResourceNotFoundException {

    private String field;

    public CustomerNotFoundException(String parameter) {
        super("Customer solicited " + parameter + " not found");
    }

    public CustomerNotFoundException(String value, String field) {
        super("Customer with " + field + " '%s' not found".formatted(value));
        this.field = field;
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public String getCode() {
        return "CUSTOMER_NOT_FOUND";
    }
}
