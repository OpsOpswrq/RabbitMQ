package com.feng.producer;

import com.feng.utils.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

public class producer_dead {
    private static String NORMAL_EXCHANGE = "NORMAL_EXCHANGE";
    private static String DEAD_EXCHANGE = "DEAD_EXCHANGE";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQUtils.getChannel();
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE,BuiltinExchangeType.DIRECT);
        for(int i = 0;i<11;i++){
            String message = "info"+i;
            channel.basicPublish(NORMAL_EXCHANGE,"zhansan",null,message.getBytes("UTF-8"));
            System.out.println(message);
        }
    }
}
