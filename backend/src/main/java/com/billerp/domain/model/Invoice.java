package com.billerp.domain.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.billerp.domain.enums.InvoiceStatus;
import com.billerp.domain.exception.InvoiceAlreadyExistsException;
import com.billerp.domain.service.InvoiceCalculator;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "invoices")
public class Invoice extends BaseEntity {
    @NotNull
    private String customerId;
    private List<InvoiceItem> items;
    private BigDecimal totalAmount;
    private InvoiceStatus status;
    private LocalDate dueDate;

    public void addItem(InvoiceItem item) {
        items.add(item);
    }

    public void recalculateTotal(InvoiceCalculator calculator) {
        this.totalAmount = calculator.calculateTotal(items);
    }

    public void pay() {
        if (status == InvoiceStatus.PAID || status == InvoiceStatus.CANCELLED) {
            throw new InvoiceAlreadyExistsException("Invoice already paid");
        }
        this.status = InvoiceStatus.PAID;
    }

    public void updateStatus(InvoiceCalculator calculator) {
        this.status = calculator.calculateStatus(this);
    }
}
