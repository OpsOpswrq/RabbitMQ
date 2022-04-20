package com.feng.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfirmConfig {
    public static final String CONFIRM_EXCHANGE_NAME = "exchange";
    public static final String CONFIRM_QUEUE_NAME = "Queue";
    public static final String BACKUP_EXCHANGE_NAME = "backup.exchange";
    public static final String BACKUP_QUEUE_NAME = "backup.queue";
    public static final String WARNING_QUEUE_NAME = "warning.queue";
    @Bean("confirmQueue")
    public Queue confirmQueue(){
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }
    @Bean("backupExchange")
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }
    @Bean("confirmExchange")
    public DirectExchange directExchange(){
        ExchangeBuilder exchangeBuilder = ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).durable(true).withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME);
        return exchangeBuilder.build();
    }
    @Bean("warningUQueue")
    public Queue warningQueue(){
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }
    @Bean
    public Binding warningBinding(@Qualifier("warningUQueue") Queue queue,@Qualifier("backupExchange") FanoutExchange backupExchange){
        return BindingBuilder.bind(queue).to(backupExchange);
    }
    @Bean("backQueue")
    public Queue backQueue(){
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }
    @Bean
    public Binding backupBinding(@Qualifier("backQueue") Queue backQueue,@Qualifier("backupExchange") FanoutExchange backupExchange){
        return BindingBuilder.bind(backQueue).to(backupExchange);
    }
    @Bean
    public Binding queueBinding(@Qualifier("confirmExchange") DirectExchange confirmExchange, @Qualifier("confirmQueue") Queue confirmQueue){
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with("key1");
    }
}
