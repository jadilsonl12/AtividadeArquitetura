package org.example.service;

import org.example.domain.Cart;
import org.example.domain.Order;

import java.util.List;


public interface IOrderService {
    Order createOrder(Cart cart);
    Order getOrderById(int id);
    List<Order> listOrders(); // Novo método
}