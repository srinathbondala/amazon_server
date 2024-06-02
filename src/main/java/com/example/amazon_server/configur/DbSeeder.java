package com.example.amazon_server.configur;


import com.example.amazon_server.models.ERole;
import com.example.amazon_server.models.Role;
import com.example.amazon_server.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            List<Role> roles = Arrays.asList(
                    new Role(ERole.ROLE_USER),
                    new Role(ERole.ROLE_MODERATOR),
                    new Role(ERole.ROLE_ADMIN)
            );
            roleRepository.saveAll(roles);
        }
    }
}
