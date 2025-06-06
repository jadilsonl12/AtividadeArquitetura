package org.example.filters;


import org.example.domain.Order;
import org.example.domain.Product;

import java.util.Map;

public class InventoryCheckFilter implements Filter {
    @Override
    public void execute(Order order) {
        for (Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            if (product.getStock() < quantity) {
                throw new RuntimeException("Produto fora de estoque: " + product.getName());
            }
        }
    }

}

