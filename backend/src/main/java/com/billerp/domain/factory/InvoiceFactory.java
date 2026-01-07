package com.billerp.domain.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.billerp.domain.enums.InvoiceStatus;
import com.billerp.domain.model.Invoice;
import com.billerp.domain.model.InvoiceItem;
import com.billerp.domain.service.InvoiceCalculator;
import com.billerp.dto.request.InvoiceCreateDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InvoiceFactory {

    private final InvoiceItemFactory itemFactory;
    private final InvoiceCalculator calculator;

    public Invoice from(InvoiceCreateDTO dto) {
        List<InvoiceItem> items = dto.items()
                .stream()
                .map(itemFactory::create)
                .toList();

        return new Invoice(
                dto.customerId(),
                items,
                calculator.calculateTotal(items),
                InvoiceStatus.PENDING,
                dto.dueDate());
    }
}
