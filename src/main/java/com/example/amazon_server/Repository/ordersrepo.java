package com.example.amazon_server.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.amazon_server.models.orders;

public interface ordersrepo extends MongoRepository<orders,String>{
    
}
