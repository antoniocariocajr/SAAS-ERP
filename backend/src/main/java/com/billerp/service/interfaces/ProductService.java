package com.billerp.service.interfaces;

import com.billerp.dto.request.ProductCreateDTO;
import com.billerp.dto.request.ProductUpdateDTO;
import com.billerp.dto.response.ProductResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponse create(ProductCreateDTO dto);

    ProductResponse findById(String id);

    Page<ProductResponse> findAll(Pageable pageable);

    ProductResponse update(ProductUpdateDTO dto);

    ProductResponse activate(String id);

    ProductResponse deactivate(String id);

    void delete(String id);
}
