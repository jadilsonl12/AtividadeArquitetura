package org.example.domain;

import java.util.Map;

public class Order {
    private int id;
    private Map<Product, Integer> products;
    private String status;
    private double total;
    private boolean isPaid;

    public Order(int id, Map<Product, Integer> products, String status, double total) {
        this.id = id;
        this.products = products;
        this.status = status;
        this.total = total;
        this.isPaid = false;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public int getId() {
        return id;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
