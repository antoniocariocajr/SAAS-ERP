package com.billerp.domain.service;

import com.billerp.domain.enums.InvoiceStatus;
import com.billerp.domain.model.Invoice;
import com.billerp.domain.model.InvoiceItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class InvoiceCalculatorTest {

    private InvoiceCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new InvoiceCalculator();
    }

    @Test
    @DisplayName("Should calculate total amount of invoice items")
    void shouldCalculateTotal() {
        InvoiceItem item1 = new InvoiceItem();
        item1.setSubTotal(new BigDecimal("100.00"));

        InvoiceItem item2 = new InvoiceItem();
        item2.setSubTotal(new BigDecimal("50.50"));

        BigDecimal total = calculator.calculateTotal(Arrays.asList(item1, item2));

        assertThat(total).isEqualByComparingTo(new BigDecimal("150.50"));
    }

    @Test
    @DisplayName("Should return zero when items list is empty")
    void shouldReturnZeroForEmptyItems() {
        BigDecimal total = calculator.calculateTotal(Collections.emptyList());
        assertThat(total).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should remain PAID if already paid")
    void shouldRemainPaid() {
        Invoice invoice = new Invoice();
        invoice.setStatus(InvoiceStatus.PAID);

        InvoiceStatus status = calculator.calculateStatus(invoice);

        assertThat(status).isEqualTo(InvoiceStatus.PAID);
    }

    @Test
    @DisplayName("Should remain CANCELLED if already cancelled")
    void shouldRemainCancelled() {
        Invoice invoice = new Invoice();
        invoice.setStatus(InvoiceStatus.CANCELLED);

        InvoiceStatus status = calculator.calculateStatus(invoice);

        assertThat(status).isEqualTo(InvoiceStatus.CANCELLED);
    }

    @Test
    @DisplayName("Should mark as OVERDUE if due date is in the past")
    void shouldMarkAsOverdue() {
        Invoice invoice = new Invoice();
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setDueDate(LocalDate.now().minusDays(1));

        InvoiceStatus status = calculator.calculateStatus(invoice);

        assertThat(status).isEqualTo(InvoiceStatus.OVERDUE);
    }

    @Test
    @DisplayName("Should remain PENDING if due date is today or in the future")
    void shouldRemainPending() {
        Invoice invoice = new Invoice();
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setDueDate(LocalDate.now());

        InvoiceStatus status = calculator.calculateStatus(invoice);

        assertThat(status).isEqualTo(InvoiceStatus.PENDING);

        invoice.setDueDate(LocalDate.now().plusDays(1));
        status = calculator.calculateStatus(invoice);
        assertThat(status).isEqualTo(InvoiceStatus.PENDING);
    }
}
