package com.example.amazon_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.amazon_server.Repository.authrepo;
import com.example.amazon_server.models.authData;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private authrepo userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      authData user = userRepository.findByEmail(username)
          .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

      return UserDetailsImpl.build(user);
    }

}
