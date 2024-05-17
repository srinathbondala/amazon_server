package com.example.amazon_server.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.amazon_server.models.product_data;

public interface productrepo extends MongoRepository<product_data,String> {
    List<product_data> findByCategory(String category);
    void deleteByCategory(String category);
}
