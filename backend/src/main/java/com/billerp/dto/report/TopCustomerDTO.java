package com.billerp.dto.report;

import java.math.BigDecimal;

public record TopCustomerDTO(
        String customerId,
        String customerName,
        BigDecimal totalPurchases) {

}
