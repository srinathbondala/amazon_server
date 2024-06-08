package com.example.amazon_server.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.amazon_server.models.CartDetails;
import com.example.amazon_server.models.comments;
import com.example.amazon_server.models.orders;
import com.example.amazon_server.services.amazonService;
import com.example.amazon_server.services.authservice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class userController {

    @Autowired
    private authservice authservice;
    @Autowired
    private amazonService amazonService;

    @PostMapping("/addToCart")
    // @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addToCart(@RequestBody CartDetails product, @RequestHeader("Authorization") String token) {
        try{
            ResponseEntity<?> status = authservice.getdatafromjwt(token);
            if(status.getStatusCode() == HttpStatus.OK){
                return ResponseEntity.ok(authservice.addToCart(status.getBody(), product));
            }
            return ResponseEntity.ok("Error");
        }
        catch(Exception e){
            return ResponseEntity.ok("Error");
        }
    }
    
    @PostMapping("/removeFromCart")
    public ResponseEntity<?> removeFromCart(@RequestBody CartDetails product, @RequestHeader("Authorization") String token) {
        try{
            ResponseEntity<?> status = authservice.getdatafromjwt(token);
            if(status.getStatusCode() == HttpStatus.OK){
                return ResponseEntity.ok(authservice.removeFromCart(status.getBody(), product));
            }
            return ResponseEntity.ok("Error");
        }
        catch(Exception e){
            return ResponseEntity.ok("Error");
        }
    }

    @GetMapping("/getCart")
    public ResponseEntity<?> getCart(@RequestHeader("Authorization") String token) {
        try{
            ResponseEntity<?> status = authservice.getdatafromjwt(token);
            if(status.getStatusCode() == HttpStatus.OK){
                return ResponseEntity.ok(authservice.getCart(status.getBody()));
            }
            return ResponseEntity.ok("Error");
        }
        catch(Exception e){
            return ResponseEntity.ok("Error");
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<?> Cart(@RequestHeader("Authorization") String token) {
        try{
            ResponseEntity<?> status = authservice.getdatafromjwt(token);
            if(status.getStatusCode() == HttpStatus.OK){
                return ResponseEntity.ok(authservice.getCartDetails(status.getBody()));
            }
            return ResponseEntity.ok("Error");
        }
        catch(Exception e){
            return ResponseEntity.ok("Error");
        }
    }

    @PostMapping("/AddToLater")
    public ResponseEntity<?> AddToWishlist(@RequestBody CartDetails product, @RequestHeader("Authorization") String token) {
        try{
            ResponseEntity<?> status = authservice.getdatafromjwt(token);
            if(status.getStatusCode() == HttpStatus.OK){
                return ResponseEntity.ok(authservice.AddToLater(status.getBody(), product));
            }
            return ResponseEntity.ok("Error");
        }
        catch(Exception e){
            return ResponseEntity.ok("Error");
        }
    }

    @PostMapping("/removeFromLater")
    public ResponseEntity<?> removeFromWishlist(@RequestBody CartDetails product, @RequestHeader("Authorization") String token) {
        try{
            ResponseEntity<?> status = authservice.getdatafromjwt(token);
            if(status.getStatusCode() == HttpStatus.OK){
                return ResponseEntity.ok(authservice.removeFromLater(status.getBody(), product));
            }
            return ResponseEntity.ok("Error");
        }
        catch(Exception e){
            return ResponseEntity.ok("Error");
        }
    }
    @GetMapping("/getUserData")
    public ResponseEntity<?> getUserData(@RequestHeader("Authorization") String token) {
        try{
            ResponseEntity<?> status = authservice.getdatafromjwt(token);
            if(status.getStatusCode() == HttpStatus.OK){
                return ResponseEntity.ok(authservice.getUserData(status.getBody()));
            }
            return ResponseEntity.ok("Error");
        }
        catch(Exception e){
            return ResponseEntity.ok("Error");
        }
    }
    @GetMapping("/isProductOrdered/{productId}")
    public ResponseEntity<?> getMethodName(@PathVariable String productId, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(authservice.isProductOrdered(productId, token));
    }
    
    @PostMapping("/addOrder")
    public ResponseEntity<?> addOrder(@RequestBody orders data,@RequestHeader("Authorization") String token){
        return authservice.addOrder(data,token);
    }

    @PostMapping("/addReview/{id}")
    public ResponseEntity<?> addReview(@RequestBody comments comment, @PathVariable String id, @RequestHeader("Authorization") String token){
        return amazonService.addReview(comment,id,token);
    }
    
}
