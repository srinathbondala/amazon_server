package com.example.amazon_server.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.amazon_server.models.Product;
import com.example.amazon_server.models.product_data;
import com.example.amazon_server.services.amazonService;

@RestController
@RequestMapping("/amazon")
@CrossOrigin(origins = "*")
public class amazonController {
    
    @Autowired
    private amazonService service;

    @GetMapping("/")
    public String welcome(){
        return "Welcome to Amazon";
    }

    @GetMapping("/data")
    public ResponseEntity<List<Product>> data(){
        HttpHeaders headers = new HttpHeaders();
    headers.setCacheControl("max-age=3600, must-revalidate");
        return ResponseEntity.ok().headers(headers).body(service.getAllProducts());
    }

    @GetMapping("/dataByCategory/{category}")
    public List<product_data> dataByCategory(@PathVariable String category){
        return service.getProductByCatagory(category);
    }

    @GetMapping("/dataByCategory/{category}/{feature}")
    public List<product_data> dataByCategory(@PathVariable String category ,@PathVariable String feature){
        return service.getProductByFeature(category,feature);
    }

    @GetMapping("/dataByid/{id}")
    public product_data dataByid(@PathVariable String id){
        return service.getProductById(id);
    }
}
