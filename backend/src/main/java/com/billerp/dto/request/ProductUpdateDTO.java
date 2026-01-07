package com.billerp.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductUpdateDTO(
        @NotNull(message = "Product ID is required") String id,
        @NotBlank(message = "Name is required") @NotNull(message = "Name is required") String name,
        String description,
        @NotNull(message = "Price is required") @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0") BigDecimal price,
        @NotBlank(message = "SKU is required") @NotNull(message = "SKU is required") String sku,
        @NotNull(message = "Stock quantity is required") @Min(value = 0, message = "Stock quantity cannot be negative") Integer stockQuantity,
        String imageUrl,
        @NotBlank(message = "Category ID is required") @NotNull(message = "Category ID is required") String categoryId) {
}
