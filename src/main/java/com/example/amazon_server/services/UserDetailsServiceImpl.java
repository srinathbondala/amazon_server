// package com.example.amazon_server.services;

// import java.util.Collection;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import com.example.amazon_server.Repository.authrepo;
// import com.example.amazon_server.models.Role;
// import com.example.amazon_server.models.authData;

// @Service
// public class UserDetailsServiceImpl implements UserDetailsService{

//      @Autowired
//     private authrepo userRepository;

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         authData user = userRepository.findByUsername(username);
//         if (user == null) {
//             throw new UsernameNotFoundException("User not found with username: " + username);
//         }
//         return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
//                 mapRolesToAuthorities(user.getRoles()));
//     }

//     private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
//         return roles.stream()
//                 .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
//                 .collect(Collectors.toList());
//     }
// }
