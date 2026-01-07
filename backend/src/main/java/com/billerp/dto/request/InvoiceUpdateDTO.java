package com.billerp.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import com.billerp.domain.enums.InvoiceStatus;

public record InvoiceUpdateDTO(
        @NotNull @NotBlank(message = "Invoice ID is required") String id,
        @NotNull(message = "Invoice status is required") InvoiceStatus status,
        @NotNull(message = "Invoice due date is required") @FutureOrPresent(message = "Invoice due date must be in the present or future") LocalDate dueDate) {
}
