package com.billerp.controller;

import com.billerp.dto.response.InvoiceResponse;
import com.billerp.service.interfaces.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/test/email")
@RequiredArgsConstructor
@Tag(name = "Test Email", description = "Endpoints for manual email testing")
public class TestEmailController {

    private final EmailService emailService;

    @PostMapping("/invoice")
    @Operation(summary = "Send a test invoice email")
    public String sendTestInvoice(@RequestParam String email, @RequestParam String name) {
        InvoiceResponse mockInvoice = new InvoiceResponse(
                "TEST-INV-001",
                "TEST-CUST",
                Collections.emptyList(),
                new BigDecimal("1250.50"),
                null,
                LocalDate.now().plusDays(15),
                null,
                null);
        emailService.sendInvoiceEmail(mockInvoice, email, name);
        return "Invoice email triggered for " + email;
    }

    @PostMapping("/reminder")
    @Operation(summary = "Send a test reminder email")
    public String sendTestReminder(@RequestParam String email, @RequestParam String name) {
        InvoiceResponse mockInvoice = new InvoiceResponse(
                "TEST-INV-999",
                "TEST-CUST",
                Collections.emptyList(),
                new BigDecimal("450.00"),
                null,
                LocalDate.now(),
                null,
                null);
        emailService.sendPaymentReminderEmail(mockInvoice, email, name);
        return "Reminder email triggered for " + email;
    }
}
