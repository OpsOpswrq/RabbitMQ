package com.feng.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import com.rabbitmq.client.Channel;
import java.util.concurrent.TimeoutException;

public class RabbitMQUtils {
    public static Channel getChannel() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.10.100");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        return channel;
    }
}
