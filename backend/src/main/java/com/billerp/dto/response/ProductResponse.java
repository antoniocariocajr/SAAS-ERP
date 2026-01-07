package com.billerp.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductResponse(
        String id,
        String name,
        String description,
        BigDecimal price,
        String sku,
        Integer stockQuantity,
        String imageUrl,
        String categoryId,
        boolean active,
        LocalDate updatedAt) {
}
