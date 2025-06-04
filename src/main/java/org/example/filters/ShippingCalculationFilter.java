package org.example.filters;

import org.example.domain.Order;

public class ShippingCalculationFilter implements Filter {
    @Override
    public void execute(Order order) {
        double shippingCost = 10.0; // Exemplo simplificado
        order.setTotal(order.getTotal() + shippingCost);
    }
}
