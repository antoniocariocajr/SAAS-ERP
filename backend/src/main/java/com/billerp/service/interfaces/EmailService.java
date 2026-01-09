package com.billerp.service.interfaces;

import com.billerp.dto.response.InvoiceResponse;

public interface EmailService {
    void sendInvoiceEmail(InvoiceResponse invoice, String customerEmail, String customerName);

    void sendPaymentReminderEmail(InvoiceResponse invoice, String customerEmail, String customerName);
}
