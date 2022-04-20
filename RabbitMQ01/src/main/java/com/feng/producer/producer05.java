package com.feng.producer;

import com.feng.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

public class producer05 {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();
        String s = UUID.randomUUID().toString();
        channel.queueDeclare("xcq",false,false,false,null);
        channel.confirmSelect();
        ConcurrentSkipListMap<Long, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();

        ConfirmCallback ackCallback = (deliveryTag,multiple)->{
            if(multiple){
                ConcurrentNavigableMap<Long, String> map = concurrentSkipListMap.headMap(deliveryTag, true);
                map.clear();
            }else{
                concurrentSkipListMap.remove(deliveryTag);
            }
        };
        ConfirmCallback nackCallback = (deliveryTag,multiple)->{
            String message = concurrentSkipListMap.get(deliveryTag);
            System.out.println("消息失败了"+message);
        };
        channel.addConfirmListener(ackCallback,nackCallback);
        long begin = System.currentTimeMillis();
        for(int i = 0;i<1000;i++){
            String message = "消息" + i;
            System.out.println(message);
            concurrentSkipListMap.put(channel.getNextPublishSeqNo(),message);
            channel.basicPublish("","xcq",null,message.getBytes());
        }
        long end = System.currentTimeMillis();
        System.out.println((end-begin)+"ms");
    }
}
