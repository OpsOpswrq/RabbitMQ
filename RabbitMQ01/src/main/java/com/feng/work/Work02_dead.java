package com.feng.work;

import com.feng.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class Work02_dead {
    private static String DEAD_QUEUE = "DEAD_QUEUE";
    private static String DEAD_EXCHANGE = "DEAD_EXCHANGE";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQUtils.getChannel();
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");
        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            System.out.println(delivery.getEnvelope().getRoutingKey()+new String(delivery.getBody(),"UTF-8"));
        };
        channel.basicConsume(DEAD_QUEUE,true,deliverCallback,(consumerTag)->{});
    }
}
