package com.billerp.service.mapper;

import com.billerp.domain.model.Invoice;
import com.billerp.domain.model.InvoiceItem;
import com.billerp.dto.response.InvoiceItemResponse;
import com.billerp.dto.response.InvoiceResponse;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {

    public InvoiceResponse toResponse(Invoice invoice) {
        return new InvoiceResponse(
                invoice.getId(),
                invoice.getCustomerId(),
                invoice.getItems().stream().map(this::toItemResponse).toList(),
                invoice.getTotalAmount(),
                invoice.getStatus(),
                invoice.getDueDate(),
                invoice.getCreatedAt(),
                invoice.getUpdatedAt());
    }

    private InvoiceItemResponse toItemResponse(InvoiceItem item) {
        return new InvoiceItemResponse(
                item.getProductId(),
                item.getProductName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getSubTotal());
    }
}
