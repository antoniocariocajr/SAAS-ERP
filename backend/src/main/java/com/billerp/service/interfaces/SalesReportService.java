package com.billerp.service.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.billerp.dto.report.MonthlyRevenueDTO;
import com.billerp.dto.report.TopProductDTO;

public interface SalesReportService {
        List<TopProductDTO> topProducts(LocalDate start, LocalDate end, int limit);

        List<MonthlyRevenueDTO> monthRevenue(int year);

}
