package com.feng.consumer;

import com.feng.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;

public class consumer_priority {
    private static final String QUEUE_NAME = "Hello";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQUtils.getChannel();
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-max-priority",10);
        map.put("x-queue-mode","lazy");
        channel.queueDeclare(QUEUE_NAME,true,false,false,map);
        System.out.println("消费者启动等待消费...");
        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            String s = new String(delivery.getBody(), "UTF-8");
            System.out.println("接收到消息"+s);
        };
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,(consumerTag)->{});
    }
}
