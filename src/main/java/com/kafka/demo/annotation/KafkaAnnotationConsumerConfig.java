package com.kafka.demo.annotation;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * 消费者配置类
 */
@Configurable
@EnableKafka
public class KafkaAnnotationConsumerConfig {
    // 1.消费者配置
    @Bean
    private static Map<String, Object> consumerConfig(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "192.168.0.6:9092,192.168.0.7:9092,192.168.0.8:9092");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    // 2.创建消费者工厂
    @Bean
    private static ConsumerFactory<String, String> consumerFactory(){
        return new DefaultKafkaConsumerFactory(consumerConfig());
    }
    // 3.配置消费者监听器工厂
    @Bean
    private static KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
            kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory =
                new ConcurrentKafkaListenerContainerFactory();
        // 注册工厂
        containerFactory.setConsumerFactory(consumerFactory());
        // 设置监听时间
        containerFactory.getContainerProperties().setPollTimeout(3000);
        return containerFactory;
    }

    // 注册消费者
    @Bean
    public KafkaAnnotationConsumerListener KafkaConsumerListener() {
        return new KafkaAnnotationConsumerListener();
    }
}
