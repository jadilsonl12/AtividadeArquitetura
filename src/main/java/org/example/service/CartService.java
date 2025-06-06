package org.example.service;

import org.example.domain.Cart;
import org.example.domain.Product;
import org.example.repository.CartRepository;


public class CartService implements ICartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public void addProductToCart(int cartId, Product product) {
        Cart cart = cartRepository.findById(cartId);
        if (cart != null) {
            cart.getProducts().merge(product, 1, Integer::sum);
        }
    }

    @Override
    public Cart getCartById(int cartId) {
        return cartRepository.findById(cartId);
    }

    @Override
    public void addCart(Cart cart) {
        cartRepository.save(cart);
    }
}
