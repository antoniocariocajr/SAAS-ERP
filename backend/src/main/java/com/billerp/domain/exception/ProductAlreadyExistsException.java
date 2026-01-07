package com.billerp.domain.exception;

public class ProductAlreadyExistsException extends BusinessException {
    public ProductAlreadyExistsException(String name) {
        super("Product already exists with name: " + name);
    }
}
