package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private Cart cart;
    private String status;
    private double total;

    public Order(int id, Cart cart, String status) {
        this.id = id;
        this.cart = cart;
        this.status = status;
        this.total = cart.getTotalPrice(); // Inicializa com o preço total do carrinho
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
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

    public List<Product> getProducts() {
        return cart.getProducts(); // Obtém os produtos diretamente do carrinho associado
    }
}
