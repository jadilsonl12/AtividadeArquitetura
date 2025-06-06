package org.example.infra;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class RabbitMQConnection {
    private static final String EXCHANGE_NAME = "";
    private static final String QUEUE_NAME = "order_updates";

    public static void publishMessage(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Configure o host conforme necessário
        factory.setUsername("guest");
        factory.setPassword("guest");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            System.out.println("Enviando mensagem: " + message);

            channel.basicPublish(EXCHANGE_NAME, QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar mensagem: " + e.getMessage(), e);
        }
    }
}

