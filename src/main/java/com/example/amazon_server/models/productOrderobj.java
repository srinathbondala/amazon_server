package com.example.amazon_server.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "productOrdersobj")
public class productOrderobj {
    @Id
    private String transactionId;
    private String product_id;
    private List<productOrder> productOrder;
    public productOrderobj() {
    }
    public productOrderobj(String product_id, List<com.example.amazon_server.models.productOrder> productOrder) {
        this.product_id = product_id;
        this.productOrder = productOrder;
    }
    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public String getProduct_id() {
        return product_id;
    }
    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
    public List<productOrder> getProductOrder() {
        return productOrder;
    }
    public void setProductOrder(List<productOrder> productOrder) {
        this.productOrder = productOrder;
    }
}
