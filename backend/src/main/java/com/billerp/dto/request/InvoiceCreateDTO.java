package com.billerp.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record InvoiceCreateDTO(
        @NotBlank(message = "Customer ID is required") @NotNull(message = "Customer ID is required") String customerId,
        @NotEmpty(message = "Invoice must have at least one item") @NotNull(message = "Invoice must have at least one item") List<InvoiceItemRequest> items,
        @NotNull(message = "Due date is required") @FutureOrPresent(message = "Due date must be in the present or future") LocalDate dueDate) {
}