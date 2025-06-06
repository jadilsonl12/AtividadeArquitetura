package org.example.infra;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitMQClient {
    private static final String QUEUE_NAME = "order_updates";

    public static void consumeMessages() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Configure o host conforme necessário
        factory.setUsername("guest");
        factory.setPassword("guest");

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            System.out.println("Aguardando mensagens...");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("\nMensagem recebida: \n" + message);

                // Confirmação manual da mensagem após o processamento
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            };

            // Passa autoAck como false para usar confirmação manual
            channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {
                System.out.println("Consumo cancelado pelo consumidor: " + consumerTag);
            });
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException("Erro ao consumir mensagens: " + e.getMessage(), e);
        }
    }
}
