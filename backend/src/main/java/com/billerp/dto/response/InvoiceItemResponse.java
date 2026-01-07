package com.billerp.dto.response;

import java.math.BigDecimal;

public record InvoiceItemResponse(
        String productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subTotal) {

}
