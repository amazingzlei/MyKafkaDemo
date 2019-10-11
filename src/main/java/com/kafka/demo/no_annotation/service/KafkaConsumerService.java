//package com.kafka.demo.no_annotation.service;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.listener.MessageListener;
//
//public class KafkaConsumerService implements MessageListener<String,String> {
//
//    // 用于接收信息，如果在配置文件中配置了该类，则如果kafka一有数据，该方法就会执行
//    @Override
//    public void onMessage(ConsumerRecord<String, String> stringStringConsumerRecord) {
//        System.out.println("收到来自kafka的信息:topic="+stringStringConsumerRecord.topic()
//        +"value="+stringStringConsumerRecord.value());
//    }
//}
