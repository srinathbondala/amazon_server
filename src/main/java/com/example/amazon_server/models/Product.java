package com.example.amazon_server.models;

// import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String title;
    private List<String> imageUrls;
    private String link;
    private String category;

    public Product() {
    }

    public Product(String title, List<String> imageUrls, String link, String category) {
        this.title = title;
        this.imageUrls = imageUrls;
        this.link = link;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String objectId) {
        this.id = objectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
