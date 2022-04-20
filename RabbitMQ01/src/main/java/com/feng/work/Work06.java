package com.feng.work;

import com.feng.utils.RabbitMQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Work06 {
    private static String QUEUE_NAME = "9743fe48-7a38-4b63-996c-11ebfbb0568b";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();
        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            String s = new String(delivery.getBody());
            System.out.println("消息为:"+s);
        };
        CancelCallback cancelCallback = (consumerTag)->{
            System.out.println(consumerTag+"消费者取消了消费接口回调逻辑");
        };
        System.out.println("c2消费者启动等待消费");
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
