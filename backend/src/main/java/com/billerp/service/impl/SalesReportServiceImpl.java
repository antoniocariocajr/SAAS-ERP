package com.billerp.service.impl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.DateOperators.DateToString;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import com.billerp.dto.report.DaylyRevenueDTO;
import com.billerp.dto.report.MonthlyRevenueDTO;
import com.billerp.dto.report.TopCustomerDTO;
import com.billerp.dto.report.TopProductDTO;
import com.billerp.service.interfaces.SalesReportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalesReportServiceImpl implements SalesReportService {

        private final MongoTemplate mongo;

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

        public List<TopCustomerDTO> topCustomersByYear(int year, int limit) {
                return topCustomers(
                                LocalDate.of(year, 1, 1),
                                LocalDate.of(year + 1, 1, 1), limit);
        }

        public List<TopCustomerDTO> topCustomersByMonth(int year, int month, int limit) {
                YearMonth ym = YearMonth.of(year, month);
                return topCustomers(
                                ym.atDay(1),
                                ym.plusMonths(1).atDay(1), limit);
        }

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

        @Override
        public List<DaylyRevenueDTO> dayRevenue(int year, int month) {
                YearMonth ym = YearMonth.of(year, month);
                LocalDate start = ym.atDay(1);
                LocalDate end = ym.plusMonths(1).atDay(1);
                Aggregation agg = newAggregation(
                                match(Criteria.where("dueDate").gte(start).lt(end)
                                                .and("status").is("PAID")),
                                group(DateToString.dateOf("dueDate").toString())
                                                .sum("totalAmount").as("revenue"),
                                sort(Sort.Direction.ASC, "_id"));

                return mongo.aggregate(agg, "invoices", DaylyRevenueDTO.class)
                                .getMappedResults();
        }

        private List<TopCustomerDTO> topCustomers(LocalDate start, LocalDate end, int limit) {
                Aggregation agg = newAggregation(
                                match(Criteria.where("dueDate").gte(start).lt(end)
                                                .and("status").is("PAID")),
                                lookup("customers", "customerId", "_id", "customer"),
                                unwind("customer"),
                                group("customerId")
                                                .first("customer.name").as("customerName")
                                                .sum("totalAmount").as("totalPurchases"),
                                project("customerName", "totalPurchases")
                                                .and("_id").as("customerId"),
                                sort(Sort.Direction.DESC, "totalPurchases"),
                                limit(limit));

                return mongo.aggregate(agg, "invoices", TopCustomerDTO.class)
                                .getMappedResults();
        }
}
