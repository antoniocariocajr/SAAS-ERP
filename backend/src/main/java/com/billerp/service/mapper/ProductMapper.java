package com.billerp.service.mapper;

import com.billerp.domain.model.Product;
import com.billerp.dto.request.ProductCreateDTO;
import com.billerp.dto.response.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductCreateDTO dto) {
        return new Product(
                dto.name(),
                dto.description(),
                dto.price(),
                dto.sku(),
                dto.stockQuantity(),
                dto.imageUrl(),
                dto.categoryId());
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getSku(),
                product.getStockQuantity(),
                product.getImageUrl(),
                product.getCategoryId(),
                product.isActive(),
                product.getUpdatedAt().toLocalDate());
    }
}
