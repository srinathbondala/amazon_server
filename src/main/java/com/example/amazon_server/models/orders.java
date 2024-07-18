package com.example.amazon_server.models;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class orders {
    @Id
    private String order_id;
    private Date order_date;
    private Boolean isDelivered;
    private Boolean isCancelled;
    private Double totalPrice;
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
    
    public orders(String order_id, Date order_date, List<orderStatus> order_status,
            Double totalPrice, Details shippingAddress, Boolean isDelivered , Boolean isCancelled, String userId) {
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
    public Date getOrder_date() {
        return order_date;
    }
    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }
    public List<orderStatus> getOrder_status() {
        return order_status;
    }
    public void setOrder_status(List<orderStatus> order_status) {
        this.order_status = order_status;
    }
    public Double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public Details getShippingAddress() {
        return shippingAddress;
    }
    public void setShippingAddress(Details shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
