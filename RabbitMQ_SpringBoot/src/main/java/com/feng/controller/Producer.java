package com.feng.controller;

import com.feng.utils.MyCallBack;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("confirm")
public class Producer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MyCallBack myCallBack;
    public static final String CONFIRM_EXCHANGE_NAME = "exchange";
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(myCallBack);
        rabbitTemplate.setConfirmCallback(myCallBack);
    }
    @GetMapping("sendMsg/{Msg}")
    public void sendMsg(@PathVariable("Msg") String msg){
        CorrelationData correlationData1 = new CorrelationData("1");
        String routineKey1 = "key1";
        rabbitTemplate.convertAndSend(CONFIRM_EXCHANGE_NAME,routineKey1,msg+routineKey1,correlationData1);
        CorrelationData correlationData2 = new CorrelationData("2");
        String routineKey2 = "key2";
        rabbitTemplate.convertAndSend(CONFIRM_EXCHANGE_NAME,routineKey2,msg+routineKey2,correlationData2);
        System.out.println("发送了消息"+msg);
    }
}
