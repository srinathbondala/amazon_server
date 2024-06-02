package com.example.amazon_server.models;

public class CartDetails {
    private String productId;
    private int quantity;
    private boolean inCart;

    public CartDetails() {
    }

    public CartDetails(String productId, int quantity, boolean inCart) {
        this.productId = productId;
        this.quantity = quantity;
        this.inCart = inCart;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

}
