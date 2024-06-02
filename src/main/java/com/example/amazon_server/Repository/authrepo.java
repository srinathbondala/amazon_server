package com.example.amazon_server.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.amazon_server.models.authData;
import java.util.Optional;


public interface authrepo extends MongoRepository<authData,String> {
    Optional<authData> findByUsername(String username);
    Optional<authData> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
