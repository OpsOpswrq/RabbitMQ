package com.feng.controller;

import com.feng.utils.MyCallBack;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ttl")
public class ProducerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @GetMapping("sendMsg/{message}")
    public void SendMsg(@PathVariable("message") String message){
//        System.out.println(message);
        rabbitTemplate.convertAndSend("X","XA","消息来自ttl为40S的队伍"+message);
        rabbitTemplate.convertAndSend("X","XB","消息来自ttl为10S的队伍"+message);
    }
    @GetMapping("sendExpirationMsg/{message}/{ttl}")
    public void sendMsg(@PathVariable("message") String message,@PathVariable("ttl") String ttl){
        rabbitTemplate.convertAndSend("X","XC",message,(correlationData)->{
            correlationData.getMessageProperties().setExpiration(ttl);
            return correlationData;
        });
        System.out.println("发送了"+ttl+message);
    }

}
