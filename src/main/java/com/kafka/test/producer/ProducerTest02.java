package com.kafka.test.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * 带回调函数的生产者api
 */
public class ProducerTest02 {
    public static void main(String[] args) {
        // 1.创建Kafka的配置
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.7:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");//设置键序列
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");//设置值序列
        // 2.创建生产者
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        // 3.发送消息
        for(int i = 0; i < 10; i++){
            producer.send(new ProducerRecord<>("myDemo", "name" + i), (recordMetadata, e) -> {
                if(e == null){
                    System.out.println("partition="+
                            recordMetadata.partition()+",offset="+recordMetadata.offset());
                }
            });
        }
        // 4.关闭资源
        producer.close();
    }
}
