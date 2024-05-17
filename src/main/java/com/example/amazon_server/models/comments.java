package com.example.amazon_server.models;

public class comments {
    private  String user_name;
    private String user_id;
    private String comment;
    private String rating;
    private String date;
    
    public comments() {
    }
    public comments(String user_name, String user_id, String comment, String rating, String date) {
        this.user_name = user_name;
        this.user_id = user_id;
        this.comment = comment;
        this.rating = rating;
        this.date = date;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
