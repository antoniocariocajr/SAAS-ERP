package com.billerp.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.billerp.domain.model.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

    boolean existsByName(String name);
}
