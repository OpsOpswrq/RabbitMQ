package com.feng.work;

import com.feng.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class Work02_topic {
    private static String TOPIC = "topic";
    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQUtils.getChannel();
        String queue = channel.queueDeclare().getQueue();
        channel.exchangeDeclare(TOPIC, BuiltinExchangeType.TOPIC);
        channel.queueBind(queue,TOPIC,"*.*.rabbit");
        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            System.out.println(consumerTag+new String(delivery.getBody(),"UTF-8")+delivery.getEnvelope().getRoutingKey());
        };
        channel.basicConsume(queue,true,deliverCallback,consumerTag->{});
    }
}
