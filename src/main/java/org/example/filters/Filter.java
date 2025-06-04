package org.example.filters;

import org.example.domain.Order;

public interface Filter {
    void execute(Order order);
}
