package com.billerp.domain.exception;

public class InvoiceNotFoundException extends ResourceNotFoundException {
    public InvoiceNotFoundException(String id) {
        super("Invoice not found with id: " + id);
    }

    @Override
    public String getField() {
        return "id";
    }

    @Override
    public String getCode() {
        return "INVOICE_NOT_FOUND";
    }
}
