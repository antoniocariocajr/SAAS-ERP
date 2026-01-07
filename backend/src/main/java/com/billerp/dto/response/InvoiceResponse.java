package com.billerp.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.billerp.domain.enums.InvoiceStatus;

public record InvoiceResponse(
        String id,
        String customerId,
        List<InvoiceItemResponse> items,
        BigDecimal totalAmount,
        InvoiceStatus status,
        LocalDate dueDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
