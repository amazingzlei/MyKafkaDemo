package com.kafka.test.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * 生产者拦截器
 */
public class InterceptorProducer implements ProducerInterceptor<String, String> {

    int success;// 发送成功的数量
    int error;// 发送失败的数量

    // 在发送数据之前调用
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        ProducerRecord<String, String> newRecord = new ProducerRecord<>(
                producerRecord.topic(),producerRecord.partition(),
                producerRecord.key(), System.currentTimeMillis()+producerRecord.value());
        return newRecord;
    }

    // 回调发送结果后执行
    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        if(recordMetadata != null){
            success++;
        }else {
            error++;
        }
    }

    // 关闭资源后执行
    @Override
    public void close() {
        System.out.println("success:"+success+",error:"+error);
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
