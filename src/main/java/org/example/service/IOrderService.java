package org.example.service;

import org.example.domain.Cart;
import org.example.domain.Order;

public interface IOrderService {
    Order createOrder(Cart cart);
    Order getOrderById(int id);
}
