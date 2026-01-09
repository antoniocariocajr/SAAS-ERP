package com.billerp.service.impl;

import com.billerp.domain.model.Customer;
import com.billerp.domain.repository.CustomerRepository;
import com.billerp.domain.validator.CustomerValidator;
import com.billerp.dto.request.CustomerCreateDTO;
import com.billerp.dto.request.CustomerUpdateDTO;
import com.billerp.dto.response.CustomerResponse;
import com.billerp.service.interfaces.CustomerService;
import com.billerp.service.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerValidator customerValidator;

    @Override
    public CustomerResponse create(CustomerCreateDTO dto) {
        customerValidator.validateCreate(dto);
        Customer customer = customerMapper.toEntity(dto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    public CustomerResponse findById(String id) {
        Customer customer = getCustomer(id);
        return customerMapper.toResponse(customer);
    }

    @Override
    public Page<CustomerResponse> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable).map(customerMapper::toResponse);
    }

    @Override
    public CustomerResponse update(CustomerUpdateDTO dto) {
        customerValidator.validateUpdate(dto);
        Customer customer = getCustomer(dto.id());
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setAddress(dto.address());
        customer.setPhone(dto.phone());
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public CustomerResponse activate(String id) {
        Customer customer = getCustomer(id);
        customer.setActive(true);
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public CustomerResponse deactivate(String id) {
        Customer customer = getCustomer(id);
        customer.setActive(false);
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public void delete(String id) {
        customerValidator.validateId(id);
        customerRepository.deleteById(id);
    }

    private Customer getCustomer(String id){
        customerValidator.validateId(id);
        return customerRepository.findById(id).orElseThrow();
    }
}
