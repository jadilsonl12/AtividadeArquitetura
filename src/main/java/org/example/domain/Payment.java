package org.example.domain;

public class Payment {
    private int id;
    private Order order;
    private double amount;
    private String method;

    public Payment(int id, Order order, double amount, String method) {
        this.id = id;
        this.order = order;
        this.amount = amount;
        this.method = method;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
