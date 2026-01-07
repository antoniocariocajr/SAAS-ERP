package com.billerp.dto.report;

import java.math.BigDecimal;

public record TopProductDTO(
        String productId,
        String productName,
        long totalQuantity,
        BigDecimal totalRevenue) {

}
