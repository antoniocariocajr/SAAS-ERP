package com.billerp.service.interfaces;

import com.billerp.dto.request.CustomerCreateDTO;
import com.billerp.dto.request.CustomerUpdateDTO;
import com.billerp.dto.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    CustomerResponse create(CustomerCreateDTO dto);

    CustomerResponse findById(String id);

    Page<CustomerResponse> findAll(Pageable pageable);

    CustomerResponse update(CustomerUpdateDTO dto);

    CustomerResponse activate(String id);

    CustomerResponse deactivate(String id);

    void delete(String id);
}
