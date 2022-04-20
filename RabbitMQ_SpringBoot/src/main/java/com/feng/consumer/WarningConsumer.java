package com.feng.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class WarningConsumer {
    public static final String WARNING_QUEUE_NAME = "warning.queue";
    @RabbitListener(queues = WARNING_QUEUE_NAME)
    public void receiveWarning(Message message){
        try {
            String msg = new String(message.getBody(),"UTF-8");
            System.out.println(msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
