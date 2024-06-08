package com.example.amazon_server.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.amazon_server.Repository.authrepo;
import com.example.amazon_server.jwt.JwtUtils;
import com.example.amazon_server.models.CartDetails;
import com.example.amazon_server.models.CartProductDetails;
import com.example.amazon_server.models.authData;
import com.example.amazon_server.models.orders;
import com.example.amazon_server.models.product_data;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.UpdateResult;

@Service
public class authservice {
    @Autowired
    authrepo authrepo;

     @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();


    public List<authData> giveAll() {
        return authrepo.findAll();
    }

    public authData getUserById(String id) {
        return authrepo.findById(id).get();
    }

    public void deleteUser(String email) {
        authrepo.deleteById(email);
    }

    public void updateUser(authData user) {
        authrepo.save(user);
    }

    public boolean checkUser(String email, String password) {
        authData user = authrepo.findById(email).get();
        if(user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public boolean checkUserExists(String email) {
        if(authrepo.existsById(email)) {
            return true;
        }
        return false;
    }

    public ResponseEntity<?> getdatafromjwt(String token) {
        try {
            String jwt = token.substring(7);  // Remove "Bearer " prefix
            String username = jwtUtils.getUserNameFromJwtToken(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() != null) {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                return ResponseEntity.ok(userDetails);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not authenticated");
            }
        } catch (ClassCastException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing user details");
        }
    }
    
    public ResponseEntity<?> addToCart(Object object, CartDetails product) {
        try {
            String userId = getUserIdFromJwt(object);
            CartAdd(userId, product);
            return ResponseEntity.ok("Product added to cart");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request");
        }
    }

    public String getUserIdFromJwt(Object object){
        try{
            String jsonString = objectMapper.writeValueAsString(object);
            JsonNode userDetailsNode = objectMapper.readTree(jsonString);
            return userDetailsNode.get("id").asText();
        }
        catch(Exception e){
            return e.getMessage();
        }
    } 

    private void CartAdd(String userId, CartDetails product) {
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(userId).and("cart.productId").is(product.getProductId()).and("cart.inCart").is(true);
        query.addCriteria(criteria);
        Update update = new Update().inc("cart.$.quantity", product.getQuantity());
        UpdateResult result = mongoTemplate.updateFirst(query, update, authData.class);
        if (result.getMatchedCount() == 0) {
            Query userQuery = new Query(Criteria.where("_id").is(userId));
            Update addToCartUpdate = new Update().push("cart", new CartDetails(product.getProductId(), product.getQuantity(), true));
            mongoTemplate.updateFirst(userQuery, addToCartUpdate, authData.class);
        }
    }

    public ResponseEntity<?> removeFromCart(Object object, CartDetails product) {
        try {
            String userId = getUserIdFromJwt(object);
            CartRemove(userId, product);
            return ResponseEntity.ok(userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request");
        }
    }

    private void CartRemove(String userId, CartDetails product) {
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(userId).and("cart.productId").is(product.getProductId()).and("cart.inCart").is(true);
        query.addCriteria(criteria);
        Update update = new Update().inc("cart.$.quantity", -product.getQuantity());
        UpdateResult result = mongoTemplate.updateFirst(query, update, authData.class);
        if (result.getMatchedCount() == 0) {
            Query userQuery = new Query(Criteria.where("_id").is(userId));
            Update addToCartUpdate = new Update().push("cart", new CartDetails(product.getProductId(), product.getQuantity(), true));
            mongoTemplate.updateFirst(userQuery, addToCartUpdate, authData.class);
        }
    }
    
    public ResponseEntity<?> AddToLater(Object object, CartDetails productId) {
        try {
            String userId = getUserIdFromJwt(object);
            return ResponseEntity.ok(LaterListAdd(userId, productId.getProductId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request");
        }
    }

    private ResponseEntity<?> LaterListAdd(String userId, String productId) {
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(userId).and("cart.productId").is(productId).and("cart.inCart").is(true);
        query.addCriteria(criteria);
        Update update = new Update().set("cart.$.inCart", false);
        UpdateResult result = mongoTemplate.updateFirst(query, update, authData.class);
        if (result.getMatchedCount() == 0) {
            return ResponseEntity.badRequest().body("Product not found in cart");
        }
        return ResponseEntity.ok("Product moved to later list");
    }

    @SuppressWarnings("null")
    public Object getCart(Object body) {
        try {
            String userId = getUserIdFromJwt(body);
            Query query = new Query(Criteria.where("_id").is(userId));
            return mongoTemplate.findOne(query, authData.class).getCart();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request");
        }
    }

    public Object removeFromLater(Object body, CartDetails product) {
        try {
            String userId = getUserIdFromJwt(body);
            return ResponseEntity.ok(LaterListRemove(userId, product.getProductId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request");
        }
    }

    private Object LaterListRemove(String userId, String productId) {
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(userId).and("cart.productId").is(productId).and("cart.inCart").is(false);
        query.addCriteria(criteria);
        Update update = new Update().set("cart.$.inCart", true);
        UpdateResult result = mongoTemplate.updateFirst(query, update, authData.class);
        if (result.getMatchedCount() == 0) {
            return ResponseEntity.badRequest().body("Product not found in later list");
        }
        return ResponseEntity.ok("Product removed from later list");
    }

    public ResponseEntity<?> getCartDetails(Object body){
        try{
            return ResponseEntity.ok(getProuctsFromCart(getCart(body)));
        }
        catch(Exception e){
            return ResponseEntity.ok("Error");
        }  
    }
    private Object getProuctsFromCart(Object cart) {
        try {
             @SuppressWarnings("unchecked")
            List<CartDetails> cartList = (List<CartDetails>) cart;
            List<CartDetails> cartItemsInCart = cartList.stream()
                    .filter(CartDetails::isInCart)
                    .collect(Collectors.toList());

            List<String> productIdsInCart = cartItemsInCart.stream()
                    .map(CartDetails::getProductId)
                    .collect(Collectors.toList());

            Query query = new Query();
            query.addCriteria(Criteria.where("Id").in(productIdsInCart));
            query.fields().include("Id", "title", "imageUrl", "price", "discount", "category","brand", "stock", "arivalDate", "subcategory");

            List<product_data> products = mongoTemplate.find(query, product_data.class);

            Map<String, product_data> productMap = products.stream()
                    .collect(Collectors.toMap(product_data::getId, product -> product));

            List<CartProductDetails> result = cartItemsInCart.stream()
                    .map(cartItem -> new CartProductDetails(
                            productMap.get(cartItem.getProductId()),
                            cartItem.getQuantity(),
                            cartItem.isInCart()
                    ))
                    .collect(Collectors.toList());
            return result;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request");
        }
    }

    public Object getUserData(Object body) {
        try {
            String userId = getUserIdFromJwt(body);
            Query query = new Query(Criteria.where("_id").is(userId));
            query.fields().exclude("password");
            return mongoTemplate.findOne(query, authData.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request");
        }
    }

    public String isProductOrdered(String productId , Object body) {
        try {
            String userId = getUserIdFromJwt(body);
            return isProductOrderedTrue(productId, userId);
        } catch (Exception e) {
            return "false";
        }
    }
    private String isProductOrderedTrue(String productId, String userId) {
        Query query = new Query(Criteria.where("orders.product_id").is(productId).and("cart.isCancelled").is(false));
        return mongoTemplate.exists(query, authData.class) ? "true " : "false";
    }

    public ResponseEntity<?>addOrder(orders data,String token) {
        try {
            String userId = getUserIdFromJwt(token);
            return ResponseEntity.ok(addOrderToDataBase(userId, data));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request");
        }
    }

    private String addOrderToDataBase(String userId, orders data) {
        try{
            Query query = new Query(Criteria.where("id").is(userId));
            Update update = new Update().push("orders", data);
            mongoTemplate.updateFirst(query, update, authData.class);
            return "Order added successfully";
        }
        catch(Exception e){
            return e.getMessage();
        }
    }
}