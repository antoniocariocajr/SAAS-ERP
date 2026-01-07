package com.billerp.domain.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.billerp.domain.enums.InvoiceStatus;
import com.billerp.domain.model.Invoice;

@Repository
public interface InvoiceRepository extends MongoRepository<Invoice, String> {

    Page<Invoice> findByCustomerId(String customerId, Pageable pageable);

    Page<Invoice> findByStatus(InvoiceStatus status, Pageable pageable);

    Page<Invoice> findByDueDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Invoice> findByCustomerIdAndStatus(String customerId, InvoiceStatus status, Pageable pageable);

    Page<Invoice> findByCustomerIdAndDueDateBetween(String customerId, LocalDate start, LocalDate end,
            Pageable pageable);

    Page<Invoice> findByDueDateAndStatus(LocalDate dueDate, InvoiceStatus status, Pageable pageable);

    Page<Invoice> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
