package com.example.amazon_server.models;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Items")
public class product_data {
    @Id
    public String id;
    private String title;
    private String imageUrl;
    private List<String> description;
    private String material;
    private String rating;
    private String ratingCount;
    private ratingtemplate ratingVal;
    private String count;
    private Double price;
    private String shipping; //brand
    private List<String> moreBuyingChoices; //makelist
    private String discount;
    private String deal;
    private String category;
    private String return_policy;
    private String warranty;
    private String brand;
    private List<comments> comments;
    private String delivary;
    private List<Map<String, String>> features;
    private String recomended_use;
    private String item_weight;
    private String item_dimensions;
    private String color;
    private String special_features;
    private String asin;
    private String stock;
    private String arivalDate;
    private String subcategory;
    private String categoryId;

    
    public product_data(String id, String title, String imageUrl, List<String> description, String material,
            String rating, String ratingCount, ratingtemplate ratingVal, String count, Double price, String shipping,
            List<String> moreBuyingChoices, String discount, String deal, String category, String return_policy,
            String warranty, String brand, List<com.example.amazon_server.models.comments> comments, String delivary,
            String recomended_use, String item_weight, String item_dimensions, String color, String special_features,
            String asin, String stock, String arivalDate, String subcategory, List<Map<String, String>> features) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.material = material;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.ratingVal = ratingVal;
        this.count = count;
        this.price = price;
        this.shipping = shipping;
        this.moreBuyingChoices = moreBuyingChoices;
        this.discount = discount;
        this.deal = deal;
        this.category = category;
        this.return_policy = return_policy;
        this.warranty = warranty;
        this.brand = brand;
        this.comments = comments;
        this.delivary = delivary;
        this.recomended_use = recomended_use;
        this.item_weight = item_weight;
        this.item_dimensions = item_dimensions;
        this.color = color;
        this.special_features = special_features;
        this.asin = asin;
        this.stock = stock;
        this.arivalDate = arivalDate;
        this.subcategory = subcategory;
        this.features = features;
    }

    // public product_data(String title, String imageUrl, String description, String material, String rating, String ratingCount, String count, String price, String shipping, String moreBuyingChoices, String discount, String deal, String category) {
        //     this.title = title;
        //     this.imageUrl = imageUrl;
        //     this.description = description;
        //     this.material = material;
        //     this.rating = rating;
        //     this.ratingCount = ratingCount;
        //     this.count = count;
        //     this.price = price;
        //     this.shipping = shipping;
        //     this.moreBuyingChoices = moreBuyingChoices;
        //     this.discount = discount;
        //     this.deal = deal;
        //     this.category = category;
        // }
    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
    
    public List<Map<String, String>> getFeatures() {
        return features;
    }

    public void setFeatures(List<Map<String, String>> features) {
        this.features = features;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getasin() {
        return asin;
    }

    public ratingtemplate getRatingVal() {
        return ratingVal;
    }
    
    public void setRatingVal(ratingtemplate ratingVal) {
        this.ratingVal = ratingVal;
    }
    public product_data() {
    }

    public void setASIN(String asin) {
        this.asin = asin;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public List<String> getMoreBuyingChoices() {
        return moreBuyingChoices;
    }
    
    public void setMoreBuyingChoices(List<String> moreBuyingChoices) {
        this.moreBuyingChoices = moreBuyingChoices;
    }

    public String getReturn_policy() {
        return return_policy;
    }

    public void setReturn_policy(String return_policy) {
        this.return_policy = return_policy;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String Brand) {
        this.brand = Brand;
    }

    public List<comments> getComments() {
        return comments;
    }

    public void setComments(List<comments> comments) {
        this.comments = comments;
    }

    public String getDelivary() {
        return delivary;
    }

    public void setDelivary(String delivary) {
        this.delivary = delivary;
    }

    public String getRecomended_use() {
        return recomended_use;
    }

    public void setRecomended_use(String Recomended_use) {
        this.recomended_use = Recomended_use;
    }

    public String getItem_weight() {
        return item_weight;
    }

    public void setItem_weight(String Item_weight) {
        this.item_weight = Item_weight;
    }

    public String getItem_dimensions() {
        return item_dimensions;
    }

    public void setItem_dimensions(String item_dimensions) {
        this.item_dimensions = item_dimensions;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSpecial_features() {
        return special_features;
    }

    public void setSpecial_features(String special_features) {
        this.special_features = special_features;
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public List<String> getDescription() {
        return description;
    }
    
    public void setDescription(List<String> description) {
        this.description = description;
    }
    
    public String getMaterial() {
        return material;
    }
    
    public void setMaterial(String material) {
        this.material = material;
    }
    
    public String getRating() {
        return rating;
    }
    
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getRatingCount() {
        return ratingCount;
    }
    
    public void setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
    }
    
    public String getCount() {
        return count;
    }
    
    public void setCount(String count) {
        this.count = count;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public String getShipping() {
        return shipping;
    }
    
    public void setShipping(String shipping) {
        this.shipping = shipping;
    }
    
    public String getDiscount() {
        return discount;
    }
    
    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDeal() {
        return deal;
    }

    public void setDeal(String deal) {
        this.deal = deal;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getCategory() {
        return category;
    }

    public String getArivalDate() {
        return arivalDate;
    }

    public void setArivalDate(String arivalDate) {
        this.arivalDate = arivalDate;
    }
}