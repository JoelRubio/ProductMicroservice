package com.joel.ecommerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.joel.ecommerce.domain.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
