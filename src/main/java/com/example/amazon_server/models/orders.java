package com.example.amazon_server.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class orders {
    @Id
    private String order_id;
    private String order_date;
    private Boolean isDelivered;
    private Boolean isCancelled;
    private String totalPrice;
    private Details shippingAddress;
    private String userId;
    private List<orderStatus> order_status;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public orders() {
    }
    
    public orders(String order_id, String order_date, List<orderStatus> order_status,
            String totalPrice, Details shippingAddress, Boolean isDelivered , Boolean isCancelled, String userId) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.order_status = order_status;
        this.totalPrice = totalPrice;
        this.shippingAddress = shippingAddress;
        this.isDelivered = isDelivered;
        this.isCancelled = isCancelled;
        this.userId = userId;
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
    public List<orderStatus> getOrder_status() {
        return order_status;
    }
    public void setOrder_status(List<orderStatus> order_status) {
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
