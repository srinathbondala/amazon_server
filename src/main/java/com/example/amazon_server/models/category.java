package com.example.amazon_server.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "category")
public class category {
    @Id
    private String id;
    private String category;
    private String mainCategory;

    public category() {
    }
    public category(String category){ 
        this.category = category;
    }
    public category(String category, String mainCategory){
        this.category = category;
        this.mainCategory = mainCategory;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}
