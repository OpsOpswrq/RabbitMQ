package com.feng.work;

import com.feng.utils.RabbitMQUtils;
import com.feng.utils.SleepUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Work04 {
    private static String QUEUE_NAME = "hello";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();
        System.out.println("c2开始接受消息了");
        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            String s = new String(delivery.getBody());
            SleepUtils.sleep(10);
            System.out.println("c2接收到了消息"+s);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        };
        CancelCallback cancelCallback = (consumerTag)->{
            System.out.println("消息被中断了");
        };
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,deliverCallback,cancelCallback);
    }
}
