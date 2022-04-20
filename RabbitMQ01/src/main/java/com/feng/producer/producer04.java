package com.feng.producer;

import com.feng.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class producer04 {
    private static String QUEUE = "feng";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMQUtils.getChannel();
        String s = UUID.randomUUID().toString();
        channel.queueDeclare(QUEUE,true,false,false,null);
        long begin = System.currentTimeMillis();
        for(int i = 0;i<1000;i++){
            String message = i+" ";
            channel.basicPublish("",QUEUE,null,message.getBytes());
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时为"+(end-begin)+"ms");
    }
}
