package com.billerp.service.impl;

import com.billerp.domain.enums.InvoiceStatus;
import com.billerp.domain.factory.InvoiceFactory;
import com.billerp.domain.factory.InvoiceItemFactory;
import com.billerp.domain.model.Invoice;
import com.billerp.domain.repository.InvoiceRepository;
import com.billerp.domain.service.InvoiceCalculator;
import com.billerp.domain.validator.InvoiceValidator;
import com.billerp.dto.request.InvoiceCreateDTO;
import com.billerp.dto.request.InvoiceItemRequest;
import com.billerp.dto.response.InvoiceResponse;
import com.billerp.service.interfaces.InvoiceService;
import com.billerp.service.mapper.InvoiceMapper;
import com.billerp.service.interfaces.EmailService;
import com.billerp.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceValidator invoiceValidator;
    private final InvoiceMapper invoiceMapper;
    private final InvoiceFactory invoiceFactory;
    private final InvoiceItemFactory invoiceItemFactory;
    private final InvoiceCalculator invoiceCalculator;
    private final EmailService emailService;
    private final CustomerRepository customerRepository;

    @Override
    public InvoiceResponse create(InvoiceCreateDTO dto) {
        invoiceValidator.validateCreate(dto);
        Invoice invoice = invoiceFactory.from(dto);
        invoiceRepository.save(invoice);

        InvoiceResponse response = invoiceMapper.toResponse(invoice);

        // Send email notification
        customerRepository.findById(invoice.getCustomerId()).ifPresent(customer -> {
            emailService.sendInvoiceEmail(response, customer.getEmail(), customer.getName());
        });

        return response;
    }

    @Override
    public InvoiceResponse findById(String id) {
        Invoice invoice = getInvoice(id);
        return invoiceMapper.toResponse(invoice);
    }

    @Override
    public Page<InvoiceResponse> findAll(Pageable pageable) {
        Page<Invoice> invoices = invoiceRepository.findAll(pageable);
        return invoices.map(invoiceMapper::toResponse);
    }

    @Override
    public Page<InvoiceResponse> findByCustomerId(String customerId, Pageable pageable) {
        Page<Invoice> invoices = invoiceRepository.findByCustomerId(customerId, pageable);
        return invoices.map(invoiceMapper::toResponse);
    }

    @Override
    public Page<InvoiceResponse> findByCustomerIdAndStatus(String customerId, InvoiceStatus status, Pageable pageable) {
        Page<Invoice> invoices = invoiceRepository.findByCustomerIdAndStatus(customerId, status, pageable);
        return invoices.map(invoiceMapper::toResponse);
    }

    @Override
    public Page<InvoiceResponse> findByCustomerIdAndDueDateBetween(String customerId, LocalDate start, LocalDate end,
            Pageable pageable) {
        Page<Invoice> invoices = invoiceRepository.findByCustomerIdAndDueDateBetween(customerId, start, end,
                pageable);
        return invoices.map(invoiceMapper::toResponse);
    }

    @Override
    public Page<InvoiceResponse> findByStatus(InvoiceStatus status, Pageable pageable) {
        Page<Invoice> invoices = invoiceRepository.findByStatus(status, pageable);
        return invoices.map(invoiceMapper::toResponse);
    }

    @Override
    public Page<InvoiceResponse> findByDueDateAndStatus(LocalDate dueDate, InvoiceStatus status, Pageable pageable) {
        Page<Invoice> invoices = invoiceRepository.findByDueDateAndStatus(dueDate, status, pageable);
        return invoices.map(invoiceMapper::toResponse);
    }

    @Override
    public Page<InvoiceResponse> findByDueDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<Invoice> invoices = invoiceRepository.findByDueDateBetween(startDate, endDate, pageable);
        return invoices.map(invoiceMapper::toResponse);
    }

    @Override
    public Page<InvoiceResponse> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        Page<Invoice> invoices = invoiceRepository.findByCreatedAtBetween(start, end, pageable);
        return invoices.map(invoiceMapper::toResponse);
    }

    @Override
    public InvoiceResponse addInvoiceItem(String id, InvoiceItemRequest item) {
        Invoice invoice = getInvoice(id);
        invoice.addItem(invoiceItemFactory.create(item));
        invoice.recalculateTotal(invoiceCalculator);
        invoiceRepository.save(invoice);
        return invoiceMapper.toResponse(invoice);
    }

    @Override
    public InvoiceResponse updateStatus(String id) {
        Invoice invoice = getInvoice(id);
        invoice.updateStatus(invoiceCalculator);
        invoiceRepository.save(invoice);
        return invoiceMapper.toResponse(invoice);
    }

    @Override
    public InvoiceResponse updateDueDate(String id, LocalDate dueDate) {
        Invoice invoice = getInvoice(id);
        invoice.setDueDate(dueDate);
        invoiceRepository.save(invoice);
        return invoiceMapper.toResponse(invoice);
    }

    @Override
    public InvoiceResponse payInvoice(String id) {
        Invoice invoice = getInvoice(id);
        invoice.pay();
        invoiceRepository.save(invoice);
        return invoiceMapper.toResponse(invoice);
    }

    @Override
    public InvoiceResponse cancelInvoice(String id) {
        Invoice invoice = getInvoice(id);
        invoice.setStatus(InvoiceStatus.CANCELLED);
        invoiceRepository.save(invoice);
        return invoiceMapper.toResponse(invoice);
    }

    @Override
    public InvoiceResponse activate(String id) {
        Invoice invoice = getInvoice(id);
        invoice.setActive(true);
        invoiceRepository.save(invoice);
        return invoiceMapper.toResponse(invoice);
    }

    @Override
    public InvoiceResponse deactivate(String id) {
        Invoice invoice = getInvoice(id);
        invoice.setActive(false);
        invoiceRepository.save(invoice);
        return invoiceMapper.toResponse(invoice);
    }

    @Override
    public void delete(String id) {
        invoiceValidator.validateId(id);
        // TODO: Add logic to delete invoice
        invoiceRepository.deleteById(id);
    }

    private Invoice getInvoice(String id) {
        invoiceValidator.validateId(id);
        return invoiceRepository.findById(id).orElseThrow();
    }
}
