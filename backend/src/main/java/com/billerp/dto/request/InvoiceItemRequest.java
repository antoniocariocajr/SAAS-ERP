package com.billerp.dto.request;

public record InvoiceItemRequest(
        String productId,
        Integer quantity) {

}
