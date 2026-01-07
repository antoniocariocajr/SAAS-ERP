package com.billerp.domain.exception;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(String id) {
        super("Product not found with id: " + id);
    }

    @Override
    public String getField() {
        return "id";
    }

    @Override
    public String getCode() {
        return "PRODUCT_NOT_FOUND";
    }
}
