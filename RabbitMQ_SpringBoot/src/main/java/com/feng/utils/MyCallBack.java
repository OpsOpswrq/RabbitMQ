package com.feng.utils;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback{
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        String routingKey = returnedMessage.getRoutingKey();
        byte[] body = returnedMessage.getMessage().getBody();
        String msg = "";
        try {
            msg = new String(body,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String replyText = returnedMessage.getReplyText();
        String exchange = returnedMessage.getExchange();
        System.out.println("消息:"+msg+"被"+exchange+"退回了，原因是"+replyText+"路由的key"+routingKey);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData!=null?correlationData.getId():"";
        if(ack){
            System.out.println("交换机收到了"+id);
        }else{
            System.out.println("未收到，原因是"+cause);
        }
    }
}
