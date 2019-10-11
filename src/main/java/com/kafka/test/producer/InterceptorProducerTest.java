package com.kafka.test.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Properties;

public class InterceptorProducerTest {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.6:9092");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");//设置ack应答
        properties.put(ProducerConfig.RETRIES_CONFIG, "3");// 设置重试次数
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);//设置批次大小
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);//设置等待时间
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);//设置缓冲区大小
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");//设置键序列
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");//设置值序列
        // 设置生产者拦截器
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,
                Arrays.asList("com.kafka.test.interceptor.InterceptorProducer"));
        // 2.创建生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        // 3.发送信息
        for(int i = 0; i < 10; i++){
            // 不带回调函数的api
            ProducerRecord producerRecord = new ProducerRecord("myDemo", "name"+i);
            producer.send(producerRecord);
        }
        // 4.关闭资源
        producer.close();
    }
}
