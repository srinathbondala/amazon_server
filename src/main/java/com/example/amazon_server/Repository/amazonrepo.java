package com.example.amazon_server.Repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.amazon_server.models.Product;


public interface amazonrepo extends MongoRepository<Product,String> {
    Product findByCategory(String category);
}