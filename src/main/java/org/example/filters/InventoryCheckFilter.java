package org.example.filters;


import org.example.domain.Order;
import org.example.domain.Product;

public class InventoryCheckFilter implements Filter {
    @Override
    public void execute(Order order) {
        for (Product product : order.getProducts()) {
            if (product.getStock() < 1) {
                throw new RuntimeException("Produto fora de estoque: " + product.getName());
            }
        }
    }
}

