package org.example.service;


import org.example.domain.Cart;
import org.example.domain.Order;
import org.example.filters.Filter;
import org.example.infra.RabbitMQConnection;
import org.example.repository.OrderRepository;

import java.util.List;

public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final List<Filter> filters;

    public OrderService(OrderRepository orderRepository, List<Filter> filters) {
        this.orderRepository = orderRepository;
        this.filters = filters;
    }

    @Override
    public Order createOrder(Cart cart) {
        int orderId = orderRepository.generateOrderId();
        String status = "Pendente"; // Status inicial
        double total = cart.getTotal(); // Calcula o total com base nos produtos

        Order order = new Order(orderId, cart.getProducts(), status, total);

        // Aplicar filtros
        for (Filter filter : filters) {
            filter.execute(order);
        }

        orderRepository.save(order);
        // envia notificação via RabbitMQ
        String mensagem = "Pedido " + order.getId() + " criado com status: " + order.getStatus();
        RabbitMQConnection.publishMessage(mensagem);
        return order;
    }

    @Override
    public Order getOrderById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    public void payOrder(int orderId) {
        Order order = orderRepository.findById(orderId);
        if (order != null && !order.isPaid()) {
            order.setPaid(true);
            order.setStatus("Confirmado");
            orderRepository.save(order);

            // Enviar atualizações de status
            try {
                RabbitMQConnection.publishMessage("Pedido confirmado");
                Thread.sleep(2000); // Pausa de 2 segundos

                RabbitMQConnection.publishMessage("Nota fiscal emitida para pedido");
                Thread.sleep(2000); // Pausa de 2 segundos

                RabbitMQConnection.publishMessage("Produto sendo preparado para envio");
                Thread.sleep(2000); // Pausa de 2 segundos

                RabbitMQConnection.publishMessage("Produto enviado!");
            } catch (InterruptedException e) {
                System.err.println("Erro ao pausar a execução: " + e.getMessage());
                Thread.currentThread().interrupt(); // Restaura o estado de interrupção da thread
            } catch (Exception e) {
                System.err.println("Erro ao enviar mensagens: " + e.getMessage());
            }
        } else {
            System.out.println("Pedido não encontrado ou já foi pago.");
        }
    }
}
