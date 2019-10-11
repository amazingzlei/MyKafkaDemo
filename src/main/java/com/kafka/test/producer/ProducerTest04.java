package com.kafka.test.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * 自定义分区器
 */
public class ProducerTest04 {
    public static void main(String[] args) {
        // 1.设置配置
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.8:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");//设置键序列
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");//设置值序列
        // 使用自定义的分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.kafka.test.MyPartiton");

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
