package com.billerp.domain.validator;

import org.springframework.stereotype.Component;

import com.billerp.domain.exception.CustomerAlreadyExistsException;
import com.billerp.domain.exception.CustomerNotFoundException;
import com.billerp.domain.model.Customer;
import com.billerp.domain.repository.CustomerRepository;
import com.billerp.dto.request.CustomerCreateDTO;
import com.billerp.dto.request.CustomerUpdateDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerValidator {

    private final CustomerRepository customerRepository;

    public void validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(id, "id");
        }
    }

    public void validateName(String name) {
        if (customerRepository.existsByName(name)) {
            throw new CustomerAlreadyExistsException(name, "name");
        }
    }

    public void validateEmail(String email) {
        if (customerRepository.existsByEmail(email)) {
            throw new CustomerAlreadyExistsException(email, "email");
        }
    }

    public void validateCreate(CustomerCreateDTO dto) {
        validateName(dto.name());
        validateEmail(dto.email());
    }

    public void validateUpdate(CustomerUpdateDTO dto) {
        validateId(dto.id());
        Customer customer = getCustomer(dto.id());
        if (dto.name() != null && !dto.name().equals(customer.getName())) {
            validateName(dto.name());
        }
        if (dto.email() != null && !dto.email().equals(customer.getEmail())) {
            validateEmail(dto.email());
        }
    }

    private Customer getCustomer(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }
}
