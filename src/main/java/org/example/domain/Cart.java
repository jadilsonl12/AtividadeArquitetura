package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int id;
    private List<Product> products = new ArrayList<>();
    private double totalPrice;

    public Cart(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        calculateTotalPrice();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private void calculateTotalPrice() {
        this.totalPrice = products.stream().mapToDouble(Product::getPrice).sum();
    }

    public void addProduct(Product product) {
        this.products.add(product);
        calculateTotalPrice();
    }
}