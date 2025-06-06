package org.example.infra;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConnection {
    private static final String HOST = "localhost";
    private static final int PORT = 5672;

    public static Connection getConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory.newConnection();
    }
}

