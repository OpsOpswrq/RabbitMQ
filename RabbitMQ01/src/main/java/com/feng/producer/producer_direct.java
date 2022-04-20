package com.feng.producer;

import com.feng.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class producer_direct {
    private static String DIRECT = "direct";

    public static void main(String[] args) throws  Exception{
        Channel channel = RabbitMQUtils.getChannel();
        channel.exchangeDeclare(DIRECT, BuiltinExchangeType.DIRECT);
        HashMap<String, String> map = new HashMap<>();
        map.put("info","这个是info接受的消息");
        map.put("error","这个是error接受的消息");
        map.put("warning","这个是warning接受的消息");
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for(Map.Entry<String, String> entrySet : entries){
            String key = entrySet.getKey();
            String value = entrySet.getValue();
            System.out.println(key+value);
            channel.basicPublish(DIRECT,key,null,value.getBytes("UTF-8"));
        }
    }
}
