package com.example.amazon_server.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
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
import org.springframework.transaction.annotation.Transactional;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.amazon_server.Repository.authrepo;
import com.example.amazon_server.jwt.JwtUtils;
import com.example.amazon_server.models.CartDetails;
import com.example.amazon_server.Repository.ordersrepo;
import com.example.amazon_server.Repository.productOrdersrepo;
import com.example.amazon_server.models.CartProductDetails;
import com.example.amazon_server.models.Details;
import com.example.amazon_server.models.authData;
import com.example.amazon_server.models.orders;
import com.example.amazon_server.models.productOrder;
import com.example.amazon_server.models.productOrderobj;
import com.example.amazon_server.models.product_data;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.UpdateResult;

@Service
public class authservice {
    @Autowired
    authrepo authrepo;

    @Autowired
    ordersrepo ordersrepo;

    @Autowired
    productOrdersrepo productOrdersrepo;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /*-----------------------------User Derails services------------------------ */

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

    /*------------------------------jwt services------------------------------------------*/

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

    /*-------------------------------Cart Services---------------------------------------*/

    public ResponseEntity<?> addToCart(Object object, CartDetails product) {
        try {
            String userId = getUserIdFromJwt(object);
            CartAdd(userId, product);
            return ResponseEntity.ok("Product added to cart");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request");
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

    public ResponseEntity<?> removeFromCart(Object object, List<CartDetails> product) {
        try {
            String userId = getUserIdFromJwt(object);
            return ResponseEntity.ok(CartRemove(userId, product));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request");
        }
    }

    private UpdateResult CartRemove(String userId, List<CartDetails> product) {
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(userId);
        query.addCriteria(criteria);
        Update update = new Update().set("cart", product);
        return mongoTemplate.updateFirst(query, update, authData.class);
    }
    
    public ResponseEntity<?> AddToLater(Object object, CartDetails productId) {
        try {
            String userId = getUserIdFromJwt(object);
            return ResponseEntity.ok(LaterListAdd(userId, productId.getProductId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request");
        }
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
            query.fields().include("Id", "title", "imageUrl", "price", "discount", "category","brand", "stock", "arivalDate", "subcategory","deal","delivery");

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

    /*--------------------------------Add Latter Servies----------------------------*/

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

    /*---------------------------------Order Services---------------------------------*/

    public String isProductOrdered(String productId , Object body) {
        try {
            String userId = getUserIdFromJwt(body);
            return isProductOrderedTrue(productId, userId);
        } catch (Exception e) {
            return "false";
        }
    }
    private String isProductOrderedTrue(String productId, String userId) {
        Query query = new Query(Criteria.where("_id").is(productId).and("productOrder.user_id").is(userId));
        return mongoTemplate.exists(query, productOrdersrepo.class) ? "true " : "false";
    }

    public ResponseEntity<?>addOrder(orders data,Object token) {
        try {
            String userId = getUserIdFromJwt(token);
            return ResponseEntity.ok(addOrderToDataBase(userId, data));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request");
        }
    }
    @Transactional
    public String addOrderToDataBase(String userId, orders data) {
        data.setUserId(userId);
        data.setIsDelivered(false);
        data.setIsCancelled(false);
        data.setOrder_date(new Date().toString());
        orders savedOrder = ordersrepo.save(data);
        String orderdata = savedOrder.getOrder_id();
        /*  Query query = new Query(Criteria.where("id").is(userId));
            Update update = new Update().push("orders",orderdata);
            mongoTemplate.updateFirst(query, update, authData.class);
        data.getOrder_status().forEach(orderStatus -> {
                    Query query1 = new Query(Criteria.where("product_id").is(orderStatus.getProduct_id()));
                    Update update1 = new Update().push("productOrder",new productOrder(orderdata,userId));
                    UpdateResult updateResult = mongoTemplate.updateFirst(query1, update1, productOrderobj.class);
                    if(updateResult.getMatchedCount() == 0){
                        productOrderobj productOrder = new productOrderobj(orderStatus.getProduct_id(),List.of(new productOrder(orderdata,userId)));
                        productOrdersrepo.save(productOrder);
                    }
                });*/
        
        ;
        return addtoOrders(data,userId,orderdata)&&addOrdertoProductOrder(data,userId,orderdata)?"Order added successfully":"Error"; 
    }
    
    private boolean addtoOrders(orders data, String userId, String orderdata){
        try{
            Query query = new Query(Criteria.where("id").is(userId));
            Update update = new Update().push("orders",orderdata);
            mongoTemplate.updateFirst(query, update, authData.class);
            
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    private boolean addOrdertoProductOrder(orders data, String userId, String orderdata) {
        try{
            data.getOrder_status().forEach(orderStatus -> {
                Query query1 = new Query(Criteria.where("product_id").is(orderStatus.getProduct_id()));
                Update update1 = new Update().push("productOrder",new productOrder(orderdata,userId));
                UpdateResult updateResult = mongoTemplate.updateFirst(query1, update1, productOrderobj.class);
                if(updateResult.getMatchedCount() == 0){
                    productOrderobj productOrder = new productOrderobj(orderStatus.getProduct_id(),List.of(new productOrder(orderdata,userId)));
                    productOrdersrepo.save(productOrder);
                }
            });
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    /*---------------------------------Address Services---------------------------------*/

    public ResponseEntity<?> updateAddress( Details details, Object token) {
        try {
            String userId = getUserIdFromJwt(token);
            return ResponseEntity.ok(updateUserAdress(details,userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request");
        }
    }
    private ResponseEntity<?> updateUserAdress(Details details, String userId) {
        try {
            Query query = new Query(Criteria.where("_id").is(userId).and("details.id").is(details.getId()));
            Update update = new Update().set("details.$.address", details.getAddress()).set("details.$.city", details.getCity()).set("details.$.state", details.getState()).set("details.$.country", details.getCountry()).set("details.$.pincode", details.getPincode()).set("details.$.phone", details.getPhone()).set("details.$.name", details.getName());
            UpdateResult updateResult = mongoTemplate.updateFirst(query, update, authData.class);
            
            if (updateResult.getMatchedCount() == 0) {
                ObjectId objectid = new ObjectId();
                query = new Query(Criteria.where("_id").is(userId));
                details.setId(objectid.toString());
                update = new Update().push("details", details);
                mongoTemplate.updateFirst(query, update, authData.class);
                return ResponseEntity.ok("Address added successfully for user: " + userId);
            } else {
                return ResponseEntity.ok("Address updated successfully for user: " + userId);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request");
        }
    }
    public ResponseEntity<?> DeleteAddress(Details details, Object token) {
        try {
            String userId = getUserIdFromJwt(token);
            return ResponseEntity.ok(deleteAddress(userId, details));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request");
        }
    }

    private ResponseEntity<?> deleteAddress(String userId, Details details) {
        Query query = new Query(Criteria.where("_id").is(userId));
        Update update = new Update().pull("details", Query.query(Criteria.where("id").is(details.getId()).and("id").ne("defaultAddress")));
        UpdateResult result = mongoTemplate.updateFirst(query, update, authData.class);
        if (result.getMatchedCount() == 0) {
            return ResponseEntity.badRequest().body("Address not found or default address cannot be deleted");
        }
        return ResponseEntity.ok("Address deleted successfully");
    }
    
    public ResponseEntity<?> defaultAddress(Details details, Object token) {
        try {
            String userId = getUserIdFromJwt(token);
            return ResponseEntity.ok(setDefaultAddress(userId, details));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request");
        }
    }
    private String setDefaultAddress( String userId, Details details) {
        Query query = new Query(Criteria.where("_id").is(userId));
        Update update = new Update().set("defaultAddress", details.getId());
        UpdateResult result = mongoTemplate.updateFirst(query, update, authData.class);
        if (result.getMatchedCount() == 0) {
            return "Address not found";
        }
        return "Default address set successfully";
    }

    /*-----------------------------------------User Profile----------------------------------------*/

    public ResponseEntity<?> updateUserName(String name, Object token, String type) {
        try {
            String userId = getUserIdFromJwt(token);
            return ResponseEntity.ok(updateUserName(userId, name, type));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request");
        }
    }
    private String updateUserName(String userId, String name , String type) {
        Query query = new Query(Criteria.where("_id").is(userId));
        Update update = new Update().set(type, name);
        UpdateResult result = mongoTemplate.updateFirst(query, update, authData.class);
        if (result.getMatchedCount() == 0) {
            return "User not found";
        }
        return "User name updated successfully";
    }

}