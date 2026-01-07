package com.billerp.domain.validator;

import com.billerp.domain.exception.CategoryAlreadyExistsException;
import com.billerp.domain.exception.CategoryNotFoundException;
import com.billerp.domain.repository.CategoryRepository;
import com.billerp.dto.request.CategoryUpdateDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryValidator {

    private final CategoryRepository repository;

    public void validateName(String name) {
        if (repository.existsByName(name)) {
            throw new CategoryAlreadyExistsException(name);
        }
    }

    public void validateId(String id) {
        if (!repository.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }
    }

    public void validateUpdate(CategoryUpdateDTO dto) {
        var category = repository.findById(dto.id())
                .orElseThrow(() -> new CategoryNotFoundException(dto.id()));
        if (!dto.name().equals(category.getName())) {
            if (repository.existsByName(dto.name())) {
                throw new CategoryAlreadyExistsException(dto.name());
            }
        }
    }
}
