// package com.example.amazon_server.services;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// // import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// // import org.springframework.security.crypto.password.PasswordEncoder;

// import com.example.amazon_server.Repository.authrepo;
// import com.example.amazon_server.models.authData;

// @Service
// public class authservice {
//     @Autowired
//     authrepo authrepo;

//     public void saveUser(authData user){
//         user.setPassword(encryptPassword(user.getPassword()));
//         authrepo.save(user);
//     }

//     public List<authData> giveAll() {
//         return authrepo.findAll();
//     }

//     public authData getUserById(String email) {
//         return authrepo.findById(email).get();
//     }

//     public void deleteUser(String email) {
//         authrepo.deleteById(email);
//     }

//     public void updateUser(authData user) {
//         authrepo.save(user);
//     }

//     public boolean checkUser(String email, String password) {
//         authData user = authrepo.findById(email).get();
//         if(user.getPassword().equals(password)) {
//             return true;
//         }
//         return false;
//     }

//     public boolean checkUserExists(String email) {
//         if(authrepo.existsById(email)) {
//             return true;
//         }
//         return false;
//     }

//     public String encryptPassword(String password) {
//         //PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//         //return passwordEncoder.encode(password);
//         return password;
//     } 
    
// }