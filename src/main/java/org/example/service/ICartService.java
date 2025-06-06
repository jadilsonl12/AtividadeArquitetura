package org.example.service;

import org.example.domain.Cart;
import org.example.domain.Product;

public interface ICartService {
    void addProductToCart(int cartId, Product product);
    Cart getCartById(int cartId);
    void addCart(Cart cart);
}
