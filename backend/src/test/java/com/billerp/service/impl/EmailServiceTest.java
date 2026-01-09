package com.billerp.service.impl;

import com.billerp.dto.response.InvoiceResponse;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private MimeMessage mimeMessage;

    @BeforeEach
    void setUp() throws Exception {
        // Reflection to set the private field fromEmail normally set by @Value
        java.lang.reflect.Field field = EmailServiceImpl.class.getDeclaredField("fromEmail");
        field.setAccessible(true);
        field.set(emailService, "noreply@billerp.com");
    }

    @Test
    void shouldSendInvoiceEmail() throws Exception {
        // Given
        InvoiceResponse invoice = new InvoiceResponse(
                "INV-001", "CUST-001", Collections.emptyList(),
                new BigDecimal("100.00"), null, LocalDate.now(), null, null);

        String customerEmail = "customer@example.com";
        String customerName = "John Doe";

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("email/invoice"), any(Context.class))).thenReturn("<html>Invoice</html>");

        // When
        emailService.sendInvoiceEmail(invoice, customerEmail, customerName);

        // Then
        verify(mailSender, timeout(1000)).send(any(MimeMessage.class));
        verify(templateEngine).process(eq("email/invoice"), any(Context.class));
    }

    @Test
    void shouldSendReminderEmail() throws Exception {
        // Given
        InvoiceResponse invoice = new InvoiceResponse(
                "INV-001", "CUST-001", Collections.emptyList(),
                new BigDecimal("100.00"), null, LocalDate.now(), null, null);

        String customerEmail = "customer@example.com";
        String customerName = "John Doe";

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("email/reminder"), any(Context.class))).thenReturn("<html>Reminder</html>");

        // When
        emailService.sendPaymentReminderEmail(invoice, customerEmail, customerName);

        // Then
        verify(mailSender, timeout(1000)).send(any(MimeMessage.class));
        verify(templateEngine).process(eq("email/reminder"), any(Context.class));
    }
}
