package com.billerp.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import com.billerp.dto.report.MonthlyRevenueDTO;
import com.billerp.dto.report.TopProductDTO;
import com.billerp.service.interfaces.SalesReportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalesReportServiceImpl implements SalesReportService {

    private final MongoTemplate mongo;

    /* 1) Produtos mais vendidos (quantidade × preço unitário) */
    @Override
    public List<TopProductDTO> topProducts(LocalDate start, LocalDate end, int limit) {

        Aggregation agg = newAggregation(
                match(Criteria.where("dueDate").gte(start).lte(end)
                        .and("status").is("PAID")), // só faturas pagas
                unwind("items"),
                group("items.productId", "items.productName") // agrupa por produto
                        .sum("items.quantity").as("totalQuantity")
                        .sum("items.subTotal").as("totalRevenue"),
                sort(Sort.Direction.DESC, "totalQuantity"),
                limit(limit),
                project("_id.productId", "_id.productName", "totalQuantity", "totalRevenue")
                        .and("_id.productId").as("productId")
                        .and("_id.productName").as("productName"));

        return mongo.aggregate(agg, "invoices", TopProductDTO.class)
                .getMappedResults();
    }

    /* 2) Receita mensal de um ano */
    @Override
    public List<MonthlyRevenueDTO> monthRevenue(int year) {

        Aggregation agg = newAggregation(
                match(Criteria.where("dueDate").gte(LocalDate.of(year, 1, 1))
                        .lt(LocalDate.of(year + 1, 1, 1))
                        .and("status").is("PAID")),
                project()
                        .andExpression("year(dueDate)").as("year")
                        .andExpression("month(dueDate)").as("month")
                        .and("totalAmount").as("amount"),
                group("year", "month")
                        .sum("amount").as("revenue"),
                sort(Sort.Direction.ASC, "_id.year", "_id.month"),
                project()
                        .and("_id.year").as("year")
                        .and("_id.month").as("month")
                        .and("revenue").as("revenue"));

        return mongo.aggregate(agg, "invoices", MonthlyRevenueDTO.class)
                .getMappedResults();
    }
}
