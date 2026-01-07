package com.billerp.domain.exception;

public class InvoiceNotFoundException extends BusinessException {
    public InvoiceNotFoundException(String id) {
        super("Invoice not found with id: " + id);
    }
}
