package com.feng.work;

import com.feng.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class Work01_direct {
    private static String DIRECT = "direct";
    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQUtils.getChannel();
        channel.exchangeDeclare(DIRECT, BuiltinExchangeType.DIRECT);
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue,DIRECT,"error");
        channel.queueBind(queue,DIRECT,"info");
        System.out.println("error,info消费者已经进行绑定");
        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            System.out.println(consumerTag);
            System.out.println(new String(delivery.getBody(),"UTF-8"));
            System.out.println(delivery.getEnvelope().getRoutingKey());
        };
        channel.basicConsume(queue,true,deliverCallback,(consumerTag)->{});
    }
}
