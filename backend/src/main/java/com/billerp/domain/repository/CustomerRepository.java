package com.billerp.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.billerp.domain.model.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    boolean existsByName(String name);

    boolean existsByEmail(String email);
}
