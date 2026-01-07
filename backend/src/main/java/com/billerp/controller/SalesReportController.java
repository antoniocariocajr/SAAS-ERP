package com.billerp.controller;

import com.billerp.dto.report.MonthlyRevenueDTO;
import com.billerp.dto.report.TopProductDTO;
import com.billerp.service.interfaces.SalesReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static com.billerp.infrastructure.constants.ApiConstants.CORS_ORIGIN;
import static com.billerp.infrastructure.constants.ApiConstants.REPORT_PATH;
import static com.billerp.infrastructure.constants.OpenApiConstants.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping(REPORT_PATH)
@RequiredArgsConstructor
@CrossOrigin(origins = CORS_ORIGIN)
@Tag(name = "Report", description = "Operations related to sales reports")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
public class SalesReportController {

    private final SalesReportService salesReportService;

    @GetMapping("/top-products")
    @Operation(summary = "Get top products", description = "Retrieve a list of top-selling products in a date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Top products retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<TopProductDTO> topProducts(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(defaultValue = "10") int limit) {
        return salesReportService.topProducts(start, end, limit);
    }

    @GetMapping("/monthly-revenue")
    @Operation(summary = "Get monthly revenue", description = "Retrieve revenue breakdown by month for a specific year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monthly revenue retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<MonthlyRevenueDTO> monthRevenue(@RequestParam int year) {
        return salesReportService.monthRevenue(year);
    }
}
