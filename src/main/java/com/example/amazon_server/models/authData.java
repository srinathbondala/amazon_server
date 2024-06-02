package com.example.amazon_server.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



@Document(collection = "auth")
public class authData {
    @Id
    private String id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;
    private String phone;
     @DBRef
    private Set<Role> roles = new HashSet<>();

    private Details details;

    private List<CartDetails> cart;
    
    private int deviceCount;
    
    private List<String> wishList;

    private List<orders> orders;
    
    public authData() {
    }

    public authData(String username, String email, String password) {
      this.username = username;
      this.email = email;
      this.password = password;
    }
    
    public List<orders> getOrders() {
      return orders;
    }

    public void setOrders(List<orders> orders) {
      this.orders = orders;
    }

    
    public void addCartItem(CartDetails orderDetails){
      this.cart.add(orderDetails);
    }

    public void removeCartItem(CartDetails orderDetails){
        this.cart.remove(orderDetails);
    }

    public Details getDetails() {
      return details;
    }
    
    public List<CartDetails> getCart() {
      return cart;
    }

    public void setCart(List<CartDetails> cart) {
      this.cart = cart;
    }

    public List<String> getWishList() {
      return wishList;
    }

    public void setWishList(List<String> wishList) {
      this.wishList = wishList;
    }

    public void setDetails(Details details) {
      this.details = details;
    }

    public String getPhone() {
      return phone;
    }
    
    public void setPhone(String phone) {
      this.phone = phone;
    }

    public int getDeviceCount() {
      return deviceCount;
    }

    public void setDeviceCount(int deviceCount) {
      this.deviceCount = deviceCount;
    }

    public String getId() {
      return id;
    }
  
    public void setId(String id) {
      this.id = id;
    }
  
    public String getUsername() {
      return username;
    }
  
    public void setUsername(String username) {
      this.username = username;
    }
  
    public String getEmail() {
      return email;
    }
  
    public void setEmail(String email) {
      this.email = email;
    }
  
    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public Set<Role> getRoles() {
      return roles;
    }
  
    public void setRoles(Set<Role> roles) {
      this.roles = roles;
    }

}
