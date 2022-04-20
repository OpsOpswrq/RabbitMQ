package com.feng.producer;

import com.feng.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class producer01 {
    private static String QUEUE_NAME = "hello";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String s = scanner.nextLine();
            channel.basicPublish("",QUEUE_NAME,null,s.getBytes());
            System.out.println("发送消息"+s);
        }
    }
}
