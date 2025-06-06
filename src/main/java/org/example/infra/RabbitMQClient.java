package org.example.infra;


import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class RabbitMQClient {
    private static final String QUEUE_NAME = "order_updates";

    public static void publishMessage(String message) {
        try (Connection connection = RabbitMQConnection.getConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("Mensagem enviada: " + message);
        } catch (Exception e) {
            System.out.println("Erro ao enviar mensagem: " + e.getMessage());
        }
    }

    public static void consumeMessages() {
        try (Connection connection = RabbitMQConnection.getConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            System.out.println("Aguardando mensagens...");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("Mensagem recebida: " + message);
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            System.out.println("Erro ao consumir mensagens: " + e.getMessage());
        }
    }
}