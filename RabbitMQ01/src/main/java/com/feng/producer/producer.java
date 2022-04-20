package com.feng.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class producer {
    public static String QUEUE_NAME = "hello";
    public static void main(String[] args) throws IOException, TimeoutException {
        System.currentTimeMillis();
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.10.100");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "hello world";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("消息发送了");
    }
}
