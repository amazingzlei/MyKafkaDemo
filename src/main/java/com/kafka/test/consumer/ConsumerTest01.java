package com.kafka.test.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * 消费者测试 自动提交 从头开始读取
 */
public class ConsumerTest01 {
    public static void main(String[] args) {
        // 1.设置配置
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.6:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");// 设置反序列化
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");

        // 设置是否自动提交offset，默认值就是true，自动提交的意思为:将当前的offset保存到kafka或者
        // zk中，这样下次启动时保证数据时从最新的开始读取，如果设置为false则内存中的offset不会保存,
        // 下一次启动时，它会从kafka或者zk中读取offset，这样会读将之前消费过的数据又消费了一次
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);// 自动提交的时间

        //　必须指定消费者组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer02");

        // 设置从头开始读取，即from-beginning,这个参数生效的时机有两种:
        // 1.处于不同的消费者组
        // 2.处于相同的消费者并且以往的数据已经失效
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 2.创建消费者
        KafkaConsumer consumer = new KafkaConsumer(properties);
        // 3.订阅主题
        consumer.subscribe(Arrays.asList("myDemo"));
        // 4.获取数据
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(10000);
            for (ConsumerRecord<String, String> consumerRecord :
                    records) {
                System.out.println("topic=" + consumerRecord.topic() +
                        ",value=" + consumerRecord.value() + ",offset=" + consumerRecord.offset());
            }
        }
    }
}
