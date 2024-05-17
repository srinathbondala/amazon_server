package com.example.amazon_server.models;

public class ratingtemplate {
    String rate1;
    String rate2;
    String rate3;
    String rate4;
    String rate5;
    
    public ratingtemplate(String rate1, String rate2, String rate3, String rate4, String rate5) {
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
        this.rate4 = rate4;
        this.rate5 = rate5;
    }
    public String getRate1() {
        return rate1;
    }
    public void setRate1(String rate1) {
        this.rate1 = rate1;
    }
    public String getRate2() {
        return rate2;
    }
    public void setRate2(String rate2) {
        this.rate2 = rate2;
    }
    public String getRate3() {
        return rate3;
    }
    public void setRate3(String rate3) {
        this.rate3 = rate3;
    }
    public String getRate4() {
        return rate4;
    }
    public void setRate4(String rate4) {
        this.rate4 = rate4;
    }
    public String getRate5() {
        return rate5;
    }
    public void setRate5(String rate5) {
        this.rate5 = rate5;
    }
    
}
