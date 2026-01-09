package com.billerp.service.impl;

import com.billerp.dto.response.InvoiceResponse;
import com.billerp.service.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @org.springframework.beans.factory.annotation.Value("${spring.mail.from}")
    private String fromEmail;

    @Async
    @Override
    public void sendInvoiceEmail(InvoiceResponse invoice, String customerEmail, String customerName) {
        log.info("Sending invoice email for invoice: {} to {}", invoice.id(), customerEmail);
        sendEmail(invoice, customerEmail, customerName, "email/invoice", "Sua Fatura SAAS-ERP");
    }

    @Async
    @Override
    public void sendPaymentReminderEmail(InvoiceResponse invoice, String customerEmail, String customerName) {
        log.info("Sending payment reminder email for invoice: {} to {}", invoice.id(), customerEmail);
        sendEmail(invoice, customerEmail, customerName, "email/reminder", "Lembrete de Pagamento - SAAS-ERP");
    }

    private void sendEmail(InvoiceResponse invoice, String customerEmail, String customerName, String templateName,
            String subject) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariable("invoice", invoice);
            context.setVariable("customerName", customerName);

            String html = templateEngine.process(templateName, context);

            helper.setFrom(fromEmail, "SAAS-ERP");
            helper.setTo(customerEmail);
            helper.setSubject(subject);
            helper.setText(html, true);

            mailSender.send(message);
            log.info("Email sent successfully to {}", customerEmail);
        } catch (MessagingException | java.io.UnsupportedEncodingException e) {
            log.error("Failed to send email to {}: {}", customerEmail, e.getMessage());
        }
    }
}
