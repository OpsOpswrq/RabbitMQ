package com.feng.producer;

import com.feng.utils.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

public class producer_priority {
    private static final String QUEUE_NAME = "Hello";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQUtils.getChannel();
        AMQP.BasicProperties build = new AMQP.BasicProperties().builder().priority(5).build();
        for(int i = 0;i<10;i++){
            String message = "info"+i;
            if(i==5){
                channel.basicPublish("",QUEUE_NAME,build,message.getBytes());
            }else{
                channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            }
            System.out.println("发送消息完成"+message);
        }
    }
}
