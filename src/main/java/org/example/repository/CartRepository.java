package org.example.repository;

import org.example.domain.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartRepository implements Repository<Cart> {
    private final List<Cart> carts = new ArrayList<>();

    @Override
    public void save(Cart cart) {
        carts.add(cart);
    }

    @Override
    public List<Cart> findAll() {
        return new ArrayList<>(carts);
    }

    @Override
    public Cart findById(int id) {
        return carts.stream()
                .filter(cart -> cart.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteById(int id) {
        carts.removeIf(cart -> cart.getId() == id);
    }
}
