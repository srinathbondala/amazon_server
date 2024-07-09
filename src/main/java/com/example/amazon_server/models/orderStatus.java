package com.example.amazon_server.models;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.util.Pair;

public class orderStatus {
    @Id
    private String order_number;
    private String product_id;
    private List<Pair<Date,String>> comments;
    private String status;
    private Double price;
    private String delivery_date;
    public orderStatus(){}
    public orderStatus(String product_id, List<Pair<Date, String>> comments, String status , Double price, String delivery_date) {
        this.product_id = product_id;
        this.comments = comments;
        this.status = status;
        this.price = price;
        this.delivery_date=delivery_date;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getDelivery_date() {
        return delivery_date;
    }
    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }
    public String getProduct_id() {
        return product_id;
    }
    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
    public String getOrder_number() {
        return order_number;
    }
    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<Pair<Date, String>> getComments() {
        return comments;
    }
    public void setComments(List<Pair<Date, String>> comments) {
        this.comments = comments;
    }
    public String getOrderNo(){
        return this.order_number;
    }
}
