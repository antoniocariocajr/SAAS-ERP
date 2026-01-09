package com.billerp.service.mapper;

import com.billerp.domain.enums.InvoiceStatus;
import com.billerp.domain.model.Invoice;
import com.billerp.domain.model.InvoiceItem;
import com.billerp.dto.response.InvoiceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class InvoiceMapperTest {

    private final InvoiceMapper mapper = new InvoiceMapper();

    @Test
    @DisplayName("Should map Invoice entity to Response DTO")
    void shouldMapToResponse() {
        InvoiceItem item = new InvoiceItem("prod1", "Product 1", 2, new BigDecimal("10.0"), new BigDecimal("20.0"));

        Invoice invoice = new Invoice();
        invoice.setId("inv1");
        invoice.setCustomerId("cust1");
        invoice.setItems(Collections.singletonList(item));
        invoice.setTotalAmount(new BigDecimal("20.0"));
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setDueDate(LocalDate.now().plusDays(7));
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setUpdatedAt(LocalDateTime.now());

        InvoiceResponse response = mapper.toResponse(invoice);

        assertThat(response.id()).isEqualTo(invoice.getId());
        assertThat(response.customerId()).isEqualTo(invoice.getCustomerId());
        assertThat(response.totalAmount()).isEqualByComparingTo(invoice.getTotalAmount());
        assertThat(response.status()).isEqualTo(invoice.getStatus());
        assertThat(response.items()).hasSize(1);
        assertThat(response.items().get(0).productName()).isEqualTo("Product 1");
    }
}
