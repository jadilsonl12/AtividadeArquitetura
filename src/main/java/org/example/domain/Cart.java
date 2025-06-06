package org.example.domain;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private int id;
    private Map<Product, Integer> products; // Produto e suas quantidades

    public Cart(int id) {
        this.id = id;
        this.products = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void addProduct(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        // Atualiza a quantidade no carrinho
        products.put(product, products.getOrDefault(product, 0) + quantity);
    }

    public double getTotal() {
        // Calcula o total com base no preço do produto e na quantidade
        return products.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public void clear() {
        products.clear(); // Limpa o carrinho
    }
}
