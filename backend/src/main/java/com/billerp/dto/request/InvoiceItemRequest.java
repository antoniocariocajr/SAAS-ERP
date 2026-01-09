package com.billerp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InvoiceItemRequest(
                @NotNull @NotBlank String productId,
                @NotNull @Positive Integer quantity) {

}
