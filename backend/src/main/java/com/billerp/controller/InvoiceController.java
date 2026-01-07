package com.billerp.controller;

import com.billerp.domain.enums.InvoiceStatus;
import com.billerp.dto.request.InvoiceCreateDTO;
import com.billerp.dto.request.InvoiceItemRequest;
import com.billerp.dto.response.InvoiceResponse;
import com.billerp.service.interfaces.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.billerp.infrastructure.constants.ApiConstants.INVOICE_PATH;
import static com.billerp.infrastructure.constants.ApiConstants.ID_PARAMETER;
import static com.billerp.infrastructure.constants.OpenApiConstants.SECURITY_SCHEME_NAME;
import static com.billerp.infrastructure.constants.ApiConstants.CORS_ORIGIN;

@RestController
@RequestMapping(INVOICE_PATH)
@RequiredArgsConstructor
@CrossOrigin(origins = CORS_ORIGIN)
@Tag(name = "Invoice", description = "Operations related to invoices")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    @Operation(summary = "Create invoice", description = "Create a new invoice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Invoice created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceResponse create(@Valid @RequestBody InvoiceCreateDTO dto) {
        return invoiceService.create(dto);
    }

    @GetMapping(ID_PARAMETER)
    @Operation(summary = "Find invoice by ID", description = "Retrieve a single invoice by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ResponseStatus(HttpStatus.OK)
    public InvoiceResponse findById(@PathVariable String id) {
        return invoiceService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Find all invoices", description = "Retrieve a paginated list of all invoices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoices found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<InvoiceResponse> findAll(Pageable pageable) {
        return invoiceService.findAll(pageable);
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Find invoices by customer", description = "Retrieve invoices associated with a specific customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoices found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ResponseStatus(HttpStatus.OK)
    public Page<InvoiceResponse> findByCustomerId(@PathVariable String customerId, Pageable pageable) {
        return invoiceService.findByCustomerId(customerId, pageable);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Find invoices by status", description = "Retrieve invoices with a specific status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoices found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @ResponseStatus(HttpStatus.OK)
    public Page<InvoiceResponse> findByStatus(@PathVariable InvoiceStatus status, Pageable pageable) {
        return invoiceService.findByStatus(status, pageable);
    }

    @GetMapping("/due-date")
    @Operation(summary = "Find invoices by due date range", description = "Retrieve invoices with due dates within a specific period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoices found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @ResponseStatus(HttpStatus.OK)
    public Page<InvoiceResponse> findByDueDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            Pageable pageable) {
        return invoiceService.findByDueDateBetween(start, end, pageable);
    }

    @PatchMapping(ID_PARAMETER + "/items")
    @Operation(summary = "Add invoice item", description = "Add a new item to an existing invoice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice item added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ResponseStatus(HttpStatus.OK)
    public InvoiceResponse addInvoiceItem(@PathVariable String id,
            @Valid @RequestBody InvoiceItemRequest item) {
        return invoiceService.addInvoiceItem(id, item);
    }

    @PatchMapping(ID_PARAMETER + "/due-date")
    @Operation(summary = "Update due date", description = "Update the due date of an invoice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice due date updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public InvoiceResponse updateDueDate(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {
        return invoiceService.updateDueDate(id, dueDate);
    }

    @PatchMapping(ID_PARAMETER + "/status")
    @Operation(summary = "Update status", description = "Trigger a status update for an invoice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ResponseStatus(HttpStatus.OK)
    public InvoiceResponse updateStatus(@PathVariable String id) {
        return invoiceService.updateStatus(id);
    }

    @PatchMapping(ID_PARAMETER + "/pay")
    @Operation(summary = "Pay invoice", description = "Mark an invoice as paid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice paid successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ResponseStatus(HttpStatus.OK)
    public InvoiceResponse payInvoice(@PathVariable String id) {
        return invoiceService.payInvoice(id);
    }

    @PatchMapping(ID_PARAMETER + "/cancel")
    @Operation(summary = "Cancel invoice", description = "Mark an invoice as cancelled")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice cancelled successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public InvoiceResponse cancelInvoice(@PathVariable String id) {
        return invoiceService.cancelInvoice(id);
    }

    @DeleteMapping(ID_PARAMETER)
    @Operation(summary = "Delete invoice", description = "Remove an invoice by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Invoice deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable String id) {
        invoiceService.delete(id);
    }
}
