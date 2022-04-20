package com.feng.work;

import com.feng.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;

public class Work01_Normal {
    private static String NORMAL_EXCHANGE = "NORMAL_EXCHANGE";
    private static String DEAD_EXCHANGE = "DEAD_EXCHANGE";
    private static String NORMAL_QUEUE = "NORMAL_QUEUE";
    private static String DEAD_QUEUE = "DEAD_QUEUE";
    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQUtils.getChannel();
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE,BuiltinExchangeType.DIRECT);
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        map.put("x-dead-letter-routing-key","lisi");
        channel.queueDeclare(NORMAL_QUEUE,false,false,false,map);
        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhansan");
        System.out.println(NORMAL_QUEUE+"在正常的接受消息");
        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            String message = new String(delivery.getBody(), "UTF-8");
            if(message.equals("info5")){
                channel.basicReject(delivery.getEnvelope().getDeliveryTag(),false);
                System.out.println("我拒收了"+message);
            }else{
                System.out.println("消费者正在接受消息"+message);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
            }
        };
        channel.basicConsume(NORMAL_QUEUE,false,deliverCallback,(consumerTag)->{});
    }
}
