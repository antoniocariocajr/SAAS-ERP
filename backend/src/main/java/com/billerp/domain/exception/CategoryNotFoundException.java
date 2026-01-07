package com.billerp.domain.exception;

public class CategoryNotFoundException extends ResourceNotFoundException {
    private String field;

    public CategoryNotFoundException(String id) {
        super("Category with id '%s' not found".formatted(id));
    }

    public CategoryNotFoundException(String value, String field) {
        super("Category with " + field + " '%s' not found".formatted(value));
        this.field = field;
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public String getCode() {
        return "CATEGORY_NOT_FOUND";
    }
}
