package org.example.service;

import org.example.domain.Cart;
import org.example.domain.Product;
import org.example.repository.Repository;

public class CartService implements ICartService {
    private final Repository<Cart> cartRepository;

    public CartService(Repository<Cart> cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public void addProductToCart(int cartId, Product product) {
        Cart cart = cartRepository.findById(cartId);
        if (cart == null) {
            cart = new Cart(cartId);
            cartRepository.save(cart);
        }
        cart.addProduct(product);
    }

    @Override
    public Cart getCartById(int cartId) {
        return cartRepository.findById(cartId);
    }
}
