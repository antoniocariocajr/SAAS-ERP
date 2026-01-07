package com.billerp.domain.exception;

public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException(String id) {
        super("Product not found with id: " + id);
    }
}
