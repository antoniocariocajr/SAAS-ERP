package com.billerp.domain.exception;

public class CategoryNotFoundException extends BusinessException {
    public CategoryNotFoundException(String id) {
        super("Category with id '%s' not found".formatted(id));
    }
}
