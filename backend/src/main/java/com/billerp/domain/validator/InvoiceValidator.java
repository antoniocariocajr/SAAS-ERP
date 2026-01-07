package com.billerp.domain.validator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.billerp.domain.exception.InvoiceNotFoundException;
import com.billerp.domain.repository.InvoiceRepository;
import com.billerp.dto.request.InvoiceCreateDTO;
import com.billerp.dto.request.InvoiceItemRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InvoiceValidator {
    private final InvoiceRepository invoiceRepository;
    private final ProductValidator productValidator;
    private final CustomerValidator customerValidator;

    public void validateId(String id) {
        if (!invoiceRepository.existsById(id)) {
            throw new InvoiceNotFoundException(id);
        }
    }

    public void validateInvoiceItems(List<InvoiceItemRequest> items) {
        for (InvoiceItemRequest item : items) {
            productValidator.validateId(item.productId());
        }
    }

    public void validateCustomer(String customerId) {
        customerValidator.validateId(customerId);
    }

    public void validateCreate(InvoiceCreateDTO dto) {
        validateCustomer(dto.customerId());
        validateInvoiceItems(dto.items());
    }
}
