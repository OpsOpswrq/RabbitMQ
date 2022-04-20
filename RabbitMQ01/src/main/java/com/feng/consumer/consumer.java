package com.feng.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class consumer {
    public static String QUEUE_NAME = "hello";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.10.100");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        System.out.println("等待接受");
        DeliverCallback deliverCallback = (consumerTag, delivery)->{
            String message = new String(delivery.getBody());
            System.out.println(message);
        };
        CancelCallback cancelCallback = (consumerTag)->{
            System.out.println("消息消费被终端了");
        };
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
