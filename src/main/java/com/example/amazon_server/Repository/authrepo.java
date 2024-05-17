package com.example.amazon_server.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.amazon_server.models.authData;

public interface authrepo extends MongoRepository<authData,String> {
    authData findByname(String username);
}
