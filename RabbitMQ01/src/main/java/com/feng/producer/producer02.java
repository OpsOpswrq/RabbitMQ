package com.feng.producer;

import com.feng.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class producer02 {
    private static String QUEUE_NAME = "hello";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.confirmSelect();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String s = scanner.nextLine();
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,s.getBytes());
            System.out.println("生产者发出消息"+s);
        }
    }
}
