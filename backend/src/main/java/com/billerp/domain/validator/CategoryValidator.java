package com.billerp.domain.validator;

import com.billerp.domain.exception.CategoryAlreadyExistsException;
import com.billerp.domain.exception.CategoryNotFoundException;
import com.billerp.domain.model.Category;
import com.billerp.domain.repository.CategoryRepository;
import com.billerp.dto.request.CategoryUpdateDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryValidator {

    private final CategoryRepository repository;

    public void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (repository.existsByName(name)) {
            throw new CategoryAlreadyExistsException(name, "name");
        }
    }

    public void validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }
        if (!repository.existsById(id)) {
            throw new CategoryNotFoundException(id, "id");
        }
    }

    public void validateUpdate(@Valid CategoryUpdateDTO dto) {
        var category = getCategory(dto.id());
        if (!dto.name().equals(category.getName())) {
            validateName(dto.name());
        }
    }

    private Category getCategory(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }
        return repository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id, "id"));
    }
}
