package com.billerp.domain.exception;

public class InvoiceAlreadyExistsException extends BusinessException {
    private String field;

    public InvoiceAlreadyExistsException(String message) {
        super(message);
    }

    public InvoiceAlreadyExistsException(String value, String field) {
        super("Invoice with " + field + " '%s' already exists".formatted(value));
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public String getCode() {
        return "INVOICE_ALREADY_EXISTS";
    }
}
