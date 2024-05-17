// package com.example.amazon_server.controler;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import com.example.amazon_server.models.authData;
// import com.example.amazon_server.services.authservice;


// @RestController
// public class authenticationController {

//     @Autowired
//     authservice authservice;
//     @RequestMapping("/auth")
//     class auth {
//         @RequestMapping("/")
//         public String welcome(){
//             return "welcome";
//         }
//         @GetMapping("/data")
//         public List<authData> getdata(){
//             return authservice.giveAll();
//         }
//         @PostMapping("/users")
//         public void createUser(@RequestBody authData user) {
//             authservice.saveUser(user);
//         }
//         @GetMapping("/checkemail/{email}")
//         public String checkemail(@PathVariable String email) {
//             if(authservice.checkUserExists(email)) {
//                 return "success";//return authservice.getUserById(email).getPassword();
//             }
//             throw new UsernameNotFoundException("User not found with email: " + email);
//         }
//         @DeleteMapping("/deleteuser/{email}")
//         public void deleteUser(@PathVariable String email) {
//             authservice.deleteUser(email);
//         }
//     }
// }
