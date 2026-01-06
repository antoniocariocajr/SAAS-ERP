package com.billerp.service.mapper;

import org.springframework.stereotype.Component;

import com.billerp.domain.model.Category;
import com.billerp.dto.request.CategoryCreateDTO;
import com.billerp.dto.response.CategoryResponse;

@Component
public class CategoryMapper {
    private CategoryMapper() {
    }

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getDescription());
    }

    public Category toModel(CategoryCreateDTO dto) {
        return new Category(dto.name(), dto.description());
    }
}
