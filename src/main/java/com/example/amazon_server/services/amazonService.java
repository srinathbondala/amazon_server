package com.example.amazon_server.services;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.example.amazon_server.Repository.amazonrepo;
import com.example.amazon_server.Repository.productrepo;
import com.example.amazon_server.models.Product;
import com.example.amazon_server.models.product_data;
import com.example.amazon_server.models.ratingtemplate;

import org.springframework.data.mongodb.core.query.Query; 
import org.springframework.data.domain.Sort;

@Service
public class amazonService {
    @Autowired
    private amazonrepo repo;
    @Autowired
    private productrepo prepo;
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
        return getProductByCatagory(category);
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

    public void saveProductData(product_data product) {
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
}
