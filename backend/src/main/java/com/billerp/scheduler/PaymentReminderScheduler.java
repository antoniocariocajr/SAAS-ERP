package com.billerp.scheduler;

import com.billerp.domain.enums.InvoiceStatus;
import com.billerp.domain.repository.CustomerRepository;
import com.billerp.domain.repository.InvoiceRepository;
import com.billerp.service.interfaces.EmailService;
import com.billerp.service.mapper.InvoiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentReminderScheduler {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final EmailService emailService;
    private final InvoiceMapper invoiceMapper;

    // Runs every day at 08:00 AM
    @Scheduled(cron = "0 0 8 * * *")
    public void sendPaymentReminders() {
        log.info("Starting payment reminder scheduled task...");

        LocalDate today = LocalDate.now();
        LocalDate threeDaysFromNow = today.plusDays(3);

        List<InvoiceStatus> statusesToRemind = List.of(InvoiceStatus.PENDING, InvoiceStatus.OVERDUE);
        List<LocalDate> datesToRemind = List.of(today, threeDaysFromNow);

        invoiceRepository.findByStatusInAndDueDateIn(statusesToRemind, datesToRemind)
                .forEach(invoice -> {
                    customerRepository.findById(invoice.getCustomerId()).ifPresent(customer -> {
                        log.info("Sending reminder for invoice {} to customer {}", invoice.getId(),
                                customer.getEmail());
                        emailService.sendPaymentReminderEmail(
                                invoiceMapper.toResponse(invoice),
                                customer.getEmail(),
                                customer.getName());
                    });
                });

        log.info("Payment reminder scheduled task completed.");
    }
}
