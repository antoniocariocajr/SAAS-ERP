package com.billerp.service.impl;

import com.billerp.domain.model.Product;
import com.billerp.domain.repository.ProductRepository;
import com.billerp.domain.validator.ProductValidator;
import com.billerp.dto.request.ProductCreateDTO;
import com.billerp.dto.request.ProductUpdateDTO;
import com.billerp.dto.response.ProductResponse;
import com.billerp.service.interfaces.ProductService;
import com.billerp.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductValidator productValidator;

    @Override
    public ProductResponse create(ProductCreateDTO dto) {
        productValidator.validateCreate(dto);
        Product product = productMapper.toEntity(dto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponse findById(String id) {
        Product product = getProduct(id);
        return productMapper.toResponse(product);
    }

    @Override
    public Page<ProductResponse> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toResponse);
    }

    @Override
    public ProductResponse update(ProductUpdateDTO dto) {
        productValidator.validateUpdate(dto);
        Product product = getProduct(dto.id());
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setSku(dto.sku());
        product.setStockQuantity(dto.stockQuantity());
        product.setImageUrl(dto.imageUrl());
        product.setCategoryId(dto.categoryId());
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse activate(String id) {
        Product product = getProduct(id);
        product.setActive(true);
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse deactivate(String id) {
        Product product = getProduct(id);
        product.setActive(false);
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public void delete(String id) {
        productValidator.validateId(id);
        productRepository.deleteById(id);
    }

    private Product getProduct(String id){
        productValidator.validateId(id);
        return productRepository.findById(id).orElseThrow();
    }
}
