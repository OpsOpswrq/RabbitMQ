package com.feng.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class TtlQueueConfig {
    public static String X_EXCHANGE = "X";
    public static String QUEUE_A = "QA";
    public static String QUEUE_B = "QB";
    public static String Y_DEAD_LETTER_EXCHANGE = "Y";
    public static String DEAD_LETTER_QUEUE = "QD";
    public static String QUEUE_C = "QC";
    @Bean("queueC")
    public Queue queueC(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        map.put("x-dead-letter-routing-key","YD");
        return QueueBuilder.durable(QUEUE_C).withArguments(map).build();
    }
    @Bean
    public Binding queueCBindingX(@Qualifier("queueC") Queue queueC,@Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }
    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }
    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }
    @Bean("queueB")
    public Queue queueB(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        map.put("x-dead-letter-routing-key","YD");
        map.put("x-message-ttl",60000);
        return QueueBuilder.durable(QUEUE_B).withArguments(map).build();
    }
    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB,@Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }
    @Bean("queueA")
    public Queue queueA(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        map.put("x-dead-letter-routing-key","YD");
        map.put("x-message-ttl",60000);
        return QueueBuilder.durable(QUEUE_A).withArguments(map).build();
    }
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA,@Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }
    @Bean("queueD")
    public Queue queueD(){
        return new Queue(DEAD_LETTER_QUEUE);
    }
    @Bean
    public Binding deadLetterBindingQD(@Qualifier("queueD") Queue queueD,@Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }
}