package com.example.amazon_server.models;

public class productOrder {
    private String order_id;
    private String user_id;
    public productOrder() {
    }
    public productOrder(String order_id, String user_id) {
        this.order_id = order_id;
        this.user_id = user_id;
    }
    public String getOrder_id() {
        return order_id;
    }
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
