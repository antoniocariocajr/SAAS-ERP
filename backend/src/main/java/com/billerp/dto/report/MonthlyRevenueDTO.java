package com.billerp.dto.report;

import java.math.BigDecimal;

public record MonthlyRevenueDTO(int year,
        int month,
        BigDecimal revenue) {

}
