package com.example.amazon_server.controler;

import org.springframework.web.bind.annotation.RestController;

import com.example.amazon_server.models.Product;
import com.example.amazon_server.models.product_data;
import com.example.amazon_server.models.ratingtemplate;
import com.example.amazon_server.services.amazonService;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private amazonService service;

    @GetMapping("/data")
    public List<product_data> data(){
        return service.getAllProductsFull();
    }

    @PostMapping("/addCategory")
    public void addCategory(@RequestBody Product product) {
        service.saveCategory(product);
    }

    @PostMapping("/addManyCategory")
    public void addManyCategory(@RequestBody List<Product> products) {
        List<Product> productsWithIds = products.stream()
            .map(product -> {
                product.setId(String.valueOf(new ObjectId()));
                return product;
            })
            .collect(Collectors.toList());
            service.saveManyCategory(productsWithIds);
        }

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody product_data product) {
        service.saveProduct(product);
    }

    @PostMapping("/addManyProducts")
    public void addManyProducts(@RequestBody List<product_data> products){
        List<product_data> productsWithIds = products.stream()
        .map(product -> {
            product.setId(String.valueOf(new ObjectId()));
            return product;
        })
        .collect(Collectors.toList());
        service.saveManyProducts(productsWithIds);
    }

    @DeleteMapping("/deleteAllProducts")
    public void deleteProducts() {
        service.deleteAll();
    }

    @DeleteMapping("/deleteCategory/{category}")
    public void deleteCategory(@PathVariable String category) {
        service.deleteCategory(category);
    }

    @DeleteMapping("/deleteproduct/{id}")
    public void deleteproduct(@PathVariable String id){
        service.deleteProducts(id);
    }

    @PutMapping("/updateproduct/{id}")
    public product_data updateproduct(@PathVariable String id, @RequestBody product_data entity) {
        return service.updateProduct(id,entity);
    }
    @PutMapping("/updaterating/{id}")
    public product_data updaterating(@PathVariable String id, @RequestBody ratingtemplate rating ){
        return service.updateRating(id, rating);
    }
}
