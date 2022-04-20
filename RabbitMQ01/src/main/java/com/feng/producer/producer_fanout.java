package com.feng.producer;

import com.feng.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

public class producer_fanout {
    private static String TOPIC = "topic";
    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQUtils.getChannel();
        channel.exchangeDeclare(TOPIC, BuiltinExchangeType.FANOUT);
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String s = scanner.nextLine();
            channel.basicPublish(TOPIC,"",null,s.getBytes("UTF-8"));
            System.out.println("生产者发出消息"+s);
        }
    }
}
