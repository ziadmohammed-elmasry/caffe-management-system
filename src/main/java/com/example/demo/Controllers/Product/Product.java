package com.example.demo.Controllers.Product;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product {
    private String productId;
    private String productName;
    private String productType;
    private int stock;
    private double price;
    private byte[] image;

    public Product(String productId, String productName, String productType, int stock, double price) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.stock = stock;
        this.price = price;
    }

    public Product(String productId, String productName, String productType, int stock, double price, byte[] image) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.stock = stock;
        this.price = price;
        this.image = image;
    }
    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productType='" + productType + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }
}