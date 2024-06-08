package com.example.amazon_server.models;

import java.util.List;

import org.springframework.data.util.Pair;

public class orders {
    private String order_id;
    private String order_date;
    private String product_id;
    private Boolean isDelivered;
    private Boolean isCancelled;
    private String delivery_date;
    private List<Pair<String,String>> order_status;
    private String totalPrice;
    private Details shippingAddress;

    public orders() {
    }
    
    public orders(String order_id, String order_date, String delivery_date, List<Pair<String, String>> order_status,
            String totalPrice, Details shippingAddress , String product_id , Boolean isDelivered , Boolean isCancelled) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.delivery_date = delivery_date;
        this.order_status = order_status;
        this.totalPrice = totalPrice;
        this.shippingAddress = shippingAddress;
        this.product_id = product_id;
        this.isDelivered = isDelivered;
        this.isCancelled = isCancelled;
    }
    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Boolean getIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(Boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    public Boolean getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public String getOrder_id() {
        return order_id;
    }
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
    public String getOrder_date() {
        return order_date;
    }
    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }
    public String getDelivery_date() {
        return delivery_date;
    }
    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }
    public List<Pair<String, String>> getOrder_status() {
        return order_status;
    }
    public void setOrder_status(List<Pair<String, String>> order_status) {
        this.order_status = order_status;
    }
    public String getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
    public Details getShippingAddress() {
        return shippingAddress;
    }
    public void setShippingAddress(Details shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
