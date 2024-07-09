package com.example.amazon_server.controler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.amazon_server.Repository.RoleRepository;
import com.example.amazon_server.Repository.authrepo;
import com.example.amazon_server.jwt.JwtUtils;
import com.example.amazon_server.models.ERole;
import com.example.amazon_server.models.Role;
import com.example.amazon_server.models.authData;
import com.example.amazon_server.payload.request.LoginRequest;
import com.example.amazon_server.payload.request.SignupRequest;
import com.example.amazon_server.payload.request.response.JwtResponse;
import com.example.amazon_server.payload.request.response.MessageResponse;
import com.example.amazon_server.services.UserDetailsImpl;
import com.example.amazon_server.services.authservice;

import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth1")
public class authenticationController {

    @Autowired
    authservice authservice;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    authrepo userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        final Logger logger = LoggerFactory.getLogger(authenticationController.class);
        try {
                logger.info("Inside authenticateUser");
                Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
                logger.info("After authenticate "+authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtUtils.generateJwtToken(authentication);
                logger.info("jwt"+jwt);
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
                List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
                logger.info("roles"+roles);
                return ResponseEntity.ok(new JwtResponse(jwt, 
                                    userDetails.getId(), 
                                    userDetails.getUsername(), 
                                    userDetails.getEmail(), 
                                    roles));    
                // return ResponseEntity.ok("success");
        } catch (AuthenticationException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body( "Invalid username or password");
        }
    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
        return ResponseEntity
            .badRequest()
            .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
        return ResponseEntity
            .badRequest()
            .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        authData user = new authData(signUpRequest.getUsername(), 
                signUpRequest.getEmail(),
                signUpRequest.getPhone(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                case "ROLE_ADMIN":
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);

                break;
                case "ROLE_MODERATOR":
                Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(modRole);

                break;
                default:
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            SecurityContextHolder.clearContext();
            
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            
            Cookie cookie = new Cookie("jwtToken", null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            
            return ResponseEntity.ok("Logout successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed");
        }
    }

    @GetMapping("/details")
    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String token) {
        final Logger logger = LoggerFactory.getLogger(authenticationController.class);
        logger.info("Inside getUserDetails");
        // try {
        //     String jwt = token.substring(7);  // Remove "Bearer " prefix
        //     String username = jwtUtils.getUserNameFromJwtToken(jwt);

        //     if (username != null && SecurityContextHolder.getContext().getAuthentication() != null) {
        //         UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //         return ResponseEntity.ok(userDetails);
        //     } else {
        //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not authenticated");
        //     }
        // } catch (ClassCastException e) {
        //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing user details");
        // }
        return authservice.getdatafromjwt(token);
    }

    @RequestMapping("/")
    public String welcome(){
        final Logger logger = LoggerFactory.getLogger(authenticationController.class);
        SecretKeySpec secretKey = (SecretKeySpec) Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        byte[] keyBytes = secretKey.getEncoded();
        String base64EncodedKey = java.util.Base64.getEncoder().encodeToString(keyBytes);


        logger.info("Generated JWT Secret: " + base64EncodedKey);
        return "welcome";
    }
    @GetMapping("/data")
    public List<authData> getdata(){
        return authservice.giveAll();
    }
    @GetMapping("/checkemail/{email}")
    public String checkemail(@PathVariable String email) {
        if(authservice.checkUserExists(email)) {
            return "success";//return authservice.getUserById(email).getPassword();
        }
        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    @PostMapping("/validateUser")
    public ResponseEntity<?> getMethodName(@Valid @RequestBody LoginRequest loginRequest, @RequestHeader("Authorization") String token) {
        try {
            String username="";
            UserDetails userDetails;
            String jwt = token.substring(7); 
            username = jwtUtils.getUserNameFromJwtToken(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() != null) {
                userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if(passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())){
                    return ResponseEntity.ok("Success");
                }
            } else {
                return ResponseEntity.badRequest().body("Invalid token or user not authenticated");
            }
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body("Error processing user details");
        }
        return ResponseEntity.ok("Invalid");
    }
    

    @DeleteMapping("/deleteuser/{email}")
    public void deleteUser(@PathVariable String email) {
        authservice.deleteUser(email);
    }
}