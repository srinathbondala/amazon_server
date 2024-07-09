package com.example.amazon_server.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.amazon_server.models.productOrderobj;

public interface productOrdersrepo extends  MongoRepository<productOrderobj, String>{
    
}
