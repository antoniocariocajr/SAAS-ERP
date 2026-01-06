package com.billerp.domain.exception;

public class CategoryAlreadyExistsException extends BusinessException {
    public CategoryAlreadyExistsException(String name) {
        super("Category with name '%s' already exists".formatted(name));
    }
}
