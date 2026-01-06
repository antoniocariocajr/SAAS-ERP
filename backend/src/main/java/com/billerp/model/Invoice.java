package com.billerp.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
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

    @DBRef
    private Customer customer;

    private List<InvoiceItem> items;
    private BigDecimal totalAmount;
    private InvoiceStatus status;
    private LocalDate dueDate;

    public enum InvoiceStatus {
        PENDING, PAID, CANCELLED, OVERDUE
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvoiceItem {
        private String productId;
        private String productName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal subTotal;
    }
}
