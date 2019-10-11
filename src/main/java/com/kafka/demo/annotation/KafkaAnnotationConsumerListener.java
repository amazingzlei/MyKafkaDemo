package com.kafka.demo.annotation;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

public class KafkaAnnotationConsumerListener {

    // @KafkaListener表示一个监听，相当于一个消费者
    @KafkaListener(topics = {"myDemo2"},groupId = "consumer04")
    public void receive(ConsumerRecord<String, String> consumerRecord){
        System.out.println("**************收到来自kafka的消息*****************");
        String value = consumerRecord.value();
        JSON json = JSON.parseObject(value);
        Person person = JSON.toJavaObject(json,Person.class);
        System.out.println(person);
        System.out.println("*************************************************");
    }
}
