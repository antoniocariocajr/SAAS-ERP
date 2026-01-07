package com.billerp.domain.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.billerp.domain.enums.InvoiceStatus;
import com.billerp.domain.model.Invoice;
import com.billerp.domain.model.InvoiceItem;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceCalculator {

    public BigDecimal calculateTotal(List<InvoiceItem> items) {
        return items.stream()
                .map(InvoiceItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public InvoiceStatus calculateStatus(Invoice invoice) {
        if (invoice.getStatus() == InvoiceStatus.PAID || invoice.getStatus() == InvoiceStatus.CANCELLED) {
            return invoice.getStatus();
        }
        if (invoice.getDueDate().isBefore(LocalDate.now())) {
            return InvoiceStatus.OVERDUE;
        } else {
            return InvoiceStatus.PENDING;
        }
    }
}