package com.billerp.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.billerp.dto.request.CategoryCreateDTO;
import com.billerp.dto.request.CategoryUpdateDTO;
import com.billerp.dto.response.CategoryResponse;
import com.billerp.domain.exception.CategoryNotFoundException;
import com.billerp.domain.model.Category;
import com.billerp.domain.repository.CategoryRepository;
import com.billerp.domain.validator.CategoryValidator;
import com.billerp.service.interfaces.CategoryService;
import com.billerp.service.mapper.CategoryMapper;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryValidator validator;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper,
            CategoryValidator validator) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.validator = validator;
    }

    @Override
    public CategoryResponse create(CategoryCreateDTO dto) {
        validator.validateName(dto.name());
        Category category = categoryMapper.toModel(dto);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public Page<CategoryResponse> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryMapper::toResponse);
    }

    @Override
    public CategoryResponse findById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse update(CategoryUpdateDTO dto) {
        validator.validateUpdate(dto);
        var category = categoryRepository.findById(dto.id()).orElseThrow();
        category.setName(dto.name());
        category.setDescription(dto.description());
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteById(String id) {
        validator.validateId(id);
        categoryRepository.deleteById(id);
    }
}
