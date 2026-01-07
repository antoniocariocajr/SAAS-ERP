package com.billerp.domain.exception;

public class CategoryAlreadyExistsException extends BusinessException {
    private String field;

    public CategoryAlreadyExistsException(String name) {
        super("Category with name '%s' already exists".formatted(name));
    }

    public CategoryAlreadyExistsException(String value, String field) {
        super("Category with " + field + " '%s' already exists".formatted(value));
        this.field = field;
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public String getCode() {
        return "CATEGORY_ALREADY_EXISTS";
    }
}
