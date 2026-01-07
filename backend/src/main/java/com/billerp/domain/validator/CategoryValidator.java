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
            throw new CategoryAlreadyExistsException(name, "name");
        }
    }

    public void validateId(String id) {
        if (!repository.existsById(id)) {
            throw new CategoryNotFoundException(id, "id");
        }
    }

    public void validateUpdate(CategoryUpdateDTO dto) {
        validateId(dto.id());
        var category = repository.findById(dto.id()).get();
        if (!dto.name().equals(category.getName())) {
            validateName(dto.name());
        }
    }
}
