package com.billerp.domain.validator;

import org.springframework.stereotype.Component;

import com.billerp.domain.exception.ProductAlreadyExistsException;
import com.billerp.domain.exception.ProductNotFoundException;
import com.billerp.domain.model.Product;
import com.billerp.domain.repository.ProductRepository;
import com.billerp.dto.request.ProductCreateDTO;
import com.billerp.dto.request.ProductUpdateDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductValidator {
    private final ProductRepository productRepository;
    private final CategoryValidator categoryValidator;

    public void validateId(String id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
    }

    public void validateName(String name) {
        if (productRepository.existsByName(name)) {
            throw new ProductAlreadyExistsException(name, "name");
        }
    }

    public void validateSku(String sku) {
        if (productRepository.existsBySku(sku)) {
            throw new ProductAlreadyExistsException(sku, "sku");
        }
    }

    public void validateCreate(ProductCreateDTO dto) {
        validateName(dto.name());
        validateSku(dto.sku());
        categoryValidator.validateId(dto.categoryId());
    }

    public void validateUpdate(ProductUpdateDTO dto) {
        validateId(dto.id());
        categoryValidator.validateId(dto.categoryId());
        Product product = productRepository.findById(dto.id()).get();
        if (!product.getName().equals(dto.name())) {
            validateName(dto.name());
        }
        if (!product.getSku().equals(dto.sku())) {
            validateSku(dto.sku());
        }
    }

}
