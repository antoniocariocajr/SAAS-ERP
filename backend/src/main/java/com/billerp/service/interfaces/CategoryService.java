package com.billerp.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.billerp.dto.request.CategoryCreateDTO;
import com.billerp.dto.request.CategoryUpdateDTO;
import com.billerp.dto.response.CategoryResponse;

public interface CategoryService {
    CategoryResponse create(CategoryCreateDTO dto);

    Page<CategoryResponse> findAll(Pageable pageable);

    CategoryResponse findById(String id);

    CategoryResponse update(CategoryUpdateDTO dto);

    void deleteById(String id);
}
