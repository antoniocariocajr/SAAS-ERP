package com.billerp.service.mapper;

import com.billerp.domain.model.Customer;
import com.billerp.dto.request.CustomerCreateDTO;
import com.billerp.dto.response.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerCreateDTO dto) {
        return new Customer(dto.name(), dto.email(), dto.phone(), dto.address());
    }

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress(),
                customer.isActive(),
                customer.getCreatedAt().toLocalDate());
    }
}
