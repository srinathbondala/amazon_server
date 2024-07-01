package com.example.amazon_server.services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.example.amazon_server.Repository.amazonrepo;
import com.example.amazon_server.Repository.catagoryrepo;
import com.example.amazon_server.Repository.productrepo;
import com.example.amazon_server.models.Product;
import com.example.amazon_server.models.category;
import com.example.amazon_server.models.comments;
import com.example.amazon_server.models.product_data;
import com.example.amazon_server.models.ratingtemplate;
import com.mongodb.client.result.UpdateResult;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Sort;

@Service
public class amazonService {
    @Autowired
    private amazonrepo repo;
    @Autowired
    private catagoryrepo crepo;
    @Autowired
    private productrepo prepo;
    @Autowired
    private authservice authservice;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<product_data> getAllProductsFull(){
        return prepo.findAll();
    }
    public List<Product> getAllProducts(){
        return repo.findAll();
    }

    public List<product_data> getProductByCatagory(String category){
        Query query = new Query(Criteria.where("category").is(category)).limit(20);
        query.fields().include("id", "category", "imageUrl", "imageTitle", "count", "rating", "ratingCount", "shipping","title","price");
        return mongoTemplate.find(query, product_data.class);
    }

    public List<product_data> getProductBySubCategory(String category,String subcategory){
        Query query = new Query(Criteria.where("category").is(category).and("subcategory").is(subcategory)).limit(20);
        query.fields().include("id", "category", "imageUrl", "imageTitle", "count", "rating", "ratingCount", "shipping","title","price");
        return mongoTemplate.find(query, product_data.class);
    }
    
    public List<product_data> getProductByFeature(String category,String feature){
        Query query = new Query(Criteria.where("category").is(category)).limit(20);
        query.fields().include("id", "category", "imageUrl", "imageTitle", "count", "rating", "ratingCount", "shipping","title","price","ArivalDate");
        if ("Price: Low to High".equals(feature)) {
            query.addCriteria(Criteria.where("price").exists(true));
            query.with(Sort.by(Sort.Order.asc("price")));

            return mongoTemplate.find(query, product_data.class);
        }
        else if("Price: High to Low".equals(feature)){
            query.addCriteria(Criteria.where("price").exists(true));
            query.with(Sort.by(Sort.Order.desc("price")));

            return mongoTemplate.find(query, product_data.class);
        }
        else if("Avg. Customer Review".equals(feature)){
            query.addCriteria(Criteria.where("rating").exists(true));
            query.with(Sort.by(Sort.Order.desc("rating")));

            return mongoTemplate.find(query, product_data.class);
        }
        else if("Newest Arrivals".equals(feature)){
            query.addCriteria(Criteria.where("ArivalDate").exists(true));
            query.with(Sort.by(Sort.Order.desc("ArivalDate")));

            return mongoTemplate.find(query, product_data.class);
        }
        else if("Best Sellers".equals(feature)){
            query.addCriteria(Criteria.where("ratingCount").exists(true));
            query.with(Sort.by(Sort.Order.desc("ratingCount")));

            return mongoTemplate.find(query, product_data.class);
        }
        else if(isValidRating(feature)){
            query.addCriteria(Criteria.where("rating").gt(feature.toCharArray()[0]).exists(true));
            query.with(Sort.by(Sort.Order.desc("rating")));
        
            return mongoTemplate.find(query, product_data.class);
        }
        else if(isValidPrice(feature)){
            String[] price = feature.split("-");
            query.addCriteria(Criteria.where("price").lt(Double.valueOf(price[1].trim())).exists(true));
            query.with(Sort.by(Sort.Order.asc("price")));

            return mongoTemplate.find(query, product_data.class);
        }
        else{
            return getProductByCatagory(category);
        }
    }
    private boolean isValidPrice(String feature) {
        String[] price = feature.split("-");
        if(price.length==2 && price[0].equals("Range")){
            return true;
        }
        else{
            return false;
        }
    }
    private static final String RATING_PATTERN = "^[1-4] Stars & Up$";
    private boolean isValidRating(String rating) {
        Pattern pattern = Pattern.compile(RATING_PATTERN);
        Matcher matcher = pattern.matcher(rating);
        return matcher.matches();
    }

    public void saveCategory(Product product){
       repo.save(product);    
    }

    public void saveManyCategory(List<Product> products) {
        repo.saveAll(products);
    }

    public void saveManyProducts(List<product_data> products){
        prepo.saveAll(products);
    }

    public product_data updateProduct(String id, product_data product) {
        if(prepo.existsById(product.getId())){
            prepo.save(product);
            return product;
        }else {
            System.out.println("Error to update : "+id);
        }
        return null;
    }
    
    public product_data updateRating(String id, ratingtemplate rating){
        try{
            if(prepo.existsById(id)){
                product_data item = prepo.findById(id).get();
                item.setRatingVal(rating);
                prepo.save(item);
                return item;
            }
            return null;
        }
        catch(Exception e){
            throw new Error("error occured while fetching by id");
        }
    }

    public product_data getProductById(String id){
        return prepo.findById(id).orElse(null);
    }

    public void saveProduct(product_data product) {
        ObjectId objectId = new ObjectId();
        product.setId(objectId.toString());
        saveProductData(product);
    }

    public void deleteProducts(String id){
        prepo.deleteById(id);
    }

    private void saveProductData(product_data product) {
       product.setCategoryId(crepo.findByCategory(product.getSubcategory()).getId());
       prepo.save(product);
    }

    public void deleteCategory(String category) {
        Product products = repo.findByCategory(category);
        repo.delete(products);
        prepo.deleteByCategory(category);
    }

    public void deleteAll(){
        prepo.deleteAll();
    }

     public ResponseEntity<?> addReview(comments comment, String id, Object token) {
        try{
            String userId = authservice.getUserIdFromJwt(token);
            return ResponseEntity.ok(addReviewToProduct(userId,comment,id));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Error processing request");
        }
    }
    private ResponseEntity<?> addReviewToProduct(String userId,comments comment,String id){
        try{
            Query query = new Query();
            Criteria criteria = Criteria.where("_id").is(id).and("comments.user_id").is(userId);
            query.addCriteria(criteria);
            Update update = new Update().set("comments.$", comment);
            UpdateResult result = mongoTemplate.updateFirst(query, update, product_data.class);
            if (result.getMatchedCount() == 0) {
                Query userQuery = new Query(Criteria.where("_id").is(id));
                Update addToCartUpdate = new Update().push("comments", comment);
                result = mongoTemplate.updateFirst(userQuery, addToCartUpdate, product_data.class);
                updateRatingFields(comment.getRating(),id);
                }
                else{
                    return ResponseEntity.ok("Already Submitted");
                    }
                return ResponseEntity.ok(result);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    private void updateRatingFields(String newrating,String productId) {
        Query query = new Query(Criteria.where("_id").is(productId));
        product_data product = mongoTemplate.findOne(query, product_data.class);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        int newRatingCount = Integer.parseInt(product.getRatingCount()) + 1;
        Update update = new Update().set("ratingCount", String.valueOf(newRatingCount));
        ratingtemplate ratingVal = product.getRatingVal();
        int newRating = Integer.parseInt(newrating);
        switch (newRating) {
            case 1:
                update.set("ratingVal.rate1", String.valueOf(Integer.parseInt(ratingVal.getRate1())+1));
                break;
            case 2:
                update.set("ratingVal.rate1", String.valueOf(Integer.parseInt(ratingVal.getRate2())+1));
                break;
            case 3:
                update.set("ratingVal.rate1", String.valueOf(Integer.parseInt(ratingVal.getRate3())+1));
                break;
            case 4:
                update.set("ratingVal.rate1", String.valueOf(Integer.parseInt(ratingVal.getRate4())+1));
                break;
            case 5:
                update.set("ratingVal.rate1", String.valueOf(Integer.parseInt(ratingVal.getRate5())+1));
                break;
            default:
                throw new IllegalArgumentException("Invalid rating value");
        }
        double newOverallRating = calculateOverallRating(ratingVal);
        update.set("rating",String.format("%.1f", newOverallRating)+" out of 5 stars");
        mongoTemplate.updateFirst(query, update, product_data.class);
    }
    private double calculateOverallRating(ratingtemplate ratingVal) {
        int rate1 = Integer.parseInt(ratingVal.getRate1());
        int rate2 = Integer.parseInt(ratingVal.getRate2());
        int rate3 = Integer.parseInt(ratingVal.getRate3());
        int rate4 = Integer.parseInt(ratingVal.getRate4());
        int rate5 = Integer.parseInt(ratingVal.getRate5());

        int totalRatings = rate1 + rate2 + rate3 + rate4 + rate5;
        int sumRatings = rate1 * 5 + rate2 * 4 + rate3 * 3 + rate4 * 2 + rate5 * 1;

        return (double) sumRatings / totalRatings;
    }

    public category addcatrgoryId(category category){
        ObjectId objectId = new ObjectId();
        category.setId(objectId.toString());
        crepo.save(category);
        return category;
    }
    public List<category> getAllCategory(){
        return crepo.findAll();
    }
    public ResponseEntity<?> addManyCategory(List<product_data> product_data){
        for( product_data product : product_data){
            if(crepo.findByCategory(product.getSubcategory()) == null)
                addcatrgoryId(new category(product.getSubcategory()));
        }
        return ResponseEntity.ok("Success");
    }
    public ResponseEntity<?> addCategoryIdProducts(List<product_data> product_data){
        for( product_data product : product_data){
            product.setCategoryId(crepo.findByCategory(product.getSubcategory()).getId());
            saveProductData(product);
        }
        return ResponseEntity.ok("Success");
    }
}
