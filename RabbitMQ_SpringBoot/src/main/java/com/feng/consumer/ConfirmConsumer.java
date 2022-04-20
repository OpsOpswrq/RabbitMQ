package com.feng.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ConfirmConsumer {
    public static final String CONFIRM_QUEUE_NAME = "Queue";
    @RabbitListener(queues = CONFIRM_QUEUE_NAME)
    public void receiveMsg(Message message) throws Exception{
        String s = new String(message.getBody(), "UTF-8");
        System.out.println(CONFIRM_QUEUE_NAME+s);
    }
}
