package org.example.ui;


import org.example.domain.Order;
import org.example.filters.Filter;
import org.example.filters.InventoryCheckFilter;
import org.example.filters.ShippingCalculationFilter;
import org.example.repository.OrderRepository;
import org.example.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminCLI {
    public static void main(String[] args) {
        List<Filter> filters = new ArrayList<>();
        filters.add(new InventoryCheckFilter());
        filters.add(new ShippingCalculationFilter());
        OrderRepository orderRepository = new OrderRepository();
        OrderService orderService = new OrderService(orderRepository, filters);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nMenu Administrador:");
            System.out.println("1. Monitorar Pedidos");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");
            int escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    // Monitorar pedidos
                    for (Order order : orderService.listOrders()) {
                        System.out.println("Pedido ID: " + order.getId() +
                                ", Status: " + order.getStatus() +
                                ", Total: R$" + order.getTotal());
                    }
                    break;

                case 2:
                    // Finalizar programa
                    running = false;
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }
}
