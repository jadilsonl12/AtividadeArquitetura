package org.example.service;


import org.example.domain.Cart;
import org.example.domain.Order;
import org.example.repository.Repository;

public class OrderService implements IOrderService {
    private final Repository<Order> orderRepository;

    public OrderService(Repository<Order> orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Cart cart) {
        int orderId = orderRepository.findAll().size() + 1;
        Order order = new Order(orderId, cart, "Pendente");
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order getOrderById(int id) {
        return orderRepository.findById(id);
    }
}
