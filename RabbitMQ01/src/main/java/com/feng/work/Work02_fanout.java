package com.feng.work;

import com.feng.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class Work02_fanout {
    private static String TOPIC = "topic";
    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQUtils.getChannel();
        channel.exchangeDeclare(TOPIC, BuiltinExchangeType.FANOUT);
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue,TOPIC,"");
        System.out.println("c2绑定成功，等待接受消息");
        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            System.out.println(consumerTag);
            System.out.println(new String(delivery.getBody(),"UTF-8"));
        };
        channel.basicConsume(queue,true,deliverCallback,(consumerTag)->{});
    }
}
