package com.billerp.domain.exception;

public class ProductAlreadyExistsException extends BusinessException {
    private String field;

    public ProductAlreadyExistsException(String name) {
        super("Product already exists with name: " + name);
    }

    public ProductAlreadyExistsException(String name, String field) {
        super("Product already exists with " + field + ": " + name);
        this.field = field;
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public String getCode() {
        return "PRODUCT_ALREADY_EXISTS";
    }
}
