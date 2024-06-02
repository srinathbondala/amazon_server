package com.example.amazon_server.Repository;

import com.example.amazon_server.models.ERole;
import com.example.amazon_server.models.Role;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String>{
    Optional<Role> findByName(ERole name);
}
