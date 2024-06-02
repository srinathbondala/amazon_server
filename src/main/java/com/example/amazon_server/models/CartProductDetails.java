package com.example.amazon_server.models;

public class CartProductDetails {
    private product_data product;
    private int quantity;
    private boolean inCart;

    public CartProductDetails(product_data product, int quantity, boolean inCart) {
        this.product = product;
        this.quantity = quantity;
        this.inCart = inCart;
    }

    public product_data getProduct() {
        return product;
    }

    public void setProduct(product_data product) {
        this.product = product;
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