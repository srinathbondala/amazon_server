package com.example.amazon_server.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.amazon_server.models.category;

public interface catagoryrepo extends MongoRepository<category,String>{
    category findByCategory(String category);
}
