package org.example.repository;

import org.example.domain.Order;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements Repository<Order> {
    private final List<Order> orders = new ArrayList<>();

    @Override
    public void save(Order order) {
        orders.add(order);
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orders);
    }

    @Override
    public Order findById(int id) {
        return orders.stream()
                .filter(order -> order.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteById(int id) {
        orders.removeIf(order -> order.getId() == id);
    }
}
