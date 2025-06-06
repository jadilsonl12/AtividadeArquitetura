package org.example.repository;

import org.example.domain.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderRepository implements Repository<Order> {
    private final List<Order> orders = new ArrayList<>();
    private final AtomicInteger orderIdGenerator = new AtomicInteger(1);

    public int generateOrderId() {
        return orderIdGenerator.getAndIncrement();
    }

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
