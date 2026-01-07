package com.billerp.service.interfaces;

import com.billerp.domain.enums.InvoiceStatus;
import com.billerp.dto.request.InvoiceCreateDTO;
import com.billerp.dto.request.InvoiceItemRequest;
import com.billerp.dto.response.InvoiceResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceService {
    InvoiceResponse create(InvoiceCreateDTO dto);

    InvoiceResponse findById(String id);

    Page<InvoiceResponse> findAll(Pageable pageable);

    Page<InvoiceResponse> findByCustomerId(String customerId, Pageable pageable);

    Page<InvoiceResponse> findByStatus(InvoiceStatus status, Pageable pageable);

    Page<InvoiceResponse> findByDueDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<InvoiceResponse> findByCustomerIdAndStatus(String customerId, InvoiceStatus status, Pageable pageable);

    Page<InvoiceResponse> findByCustomerIdAndDueDateBetween(String customerId, LocalDate start, LocalDate end,
            Pageable pageable);

    Page<InvoiceResponse> findByDueDateAndStatus(LocalDate dueDate, InvoiceStatus status, Pageable pageable);

    Page<InvoiceResponse> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    InvoiceResponse addInvoiceItem(String id, InvoiceItemRequest item);

    InvoiceResponse updateDueDate(String id, LocalDate dueDate);

    InvoiceResponse updateStatus(String id);

    InvoiceResponse payInvoice(String id);

    InvoiceResponse cancelInvoice(String id);

    InvoiceResponse activate(String id);

    InvoiceResponse deactivate(String id);

    void delete(String id);
}
