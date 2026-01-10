package com.billerp.domain.factory;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.billerp.domain.exception.ProductNotFoundException;
import com.billerp.domain.model.InvoiceItem;
import com.billerp.domain.model.Product;
import com.billerp.domain.repository.ProductRepository;
import com.billerp.dto.request.InvoiceItemRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InvoiceItemFactory {
        private final ProductRepository productRepository;

        public InvoiceItem create(InvoiceItemRequest req) {
                Product product = getProduct(req.productId());

                BigDecimal subTotal = product.getPrice()
                                .multiply(BigDecimal.valueOf(req.quantity()));

                return new InvoiceItem(
                                product.getId(),
                                product.getName(),
                                req.quantity(),
                                product.getPrice(),
                                subTotal);
        }

        private Product getProduct(String id) {
                if (id == null || id.trim().isEmpty()) {
                        throw new IllegalArgumentException("Id cannot be null or empty");
                }
                return productRepository.findById(id)
                                .orElseThrow(() -> new ProductNotFoundException(id));
        }
}
