package com.kafka.demo.annotation;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ExecutionException;


@Controller
public class KafkaAnnotationController {
    private static final Logger logger = LoggerFactory.getLogger(KafkaAnnotationController.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/testAnnotation")
    public String test() throws ExecutionException, InterruptedException {
        logger.info("****************发送Kafka信息***************");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "张三");
        jsonObject.put("age", 50);
        jsonObject.put("address", "唱、跳、rap");
        ListenableFuture<SendResult<String, String>> sendResult =
                kafkaTemplate.send("myDemo2", jsonObject.toString());
        sendResult.addCallback(new CallBackSuccess(),new FailCallBack(
                sendResult.get().getProducerRecord().topic(),
                sendResult.get().getProducerRecord().key(),
                sendResult.get().getProducerRecord().value()));
        logger.info("***************发送结束*****************************");
        return "index";
    }

    class CallBackSuccess implements SuccessCallback{

        @Override
        public void onSuccess(Object o) {
            System.out.println("发送成功!");
        }
    }

    class FailCallBack implements FailureCallback {
        String topic;
        String key;
        String data;

        FailCallBack(String topic, String key, String data){
            this.data = data;
            this.key = key;
            this.topic = topic;
        }
        @Override
        public void onFailure(Throwable throwable) {
            System.out.println("失败 topid:"+topic+",key:"+key+",data:"+data);
            throwable.printStackTrace();
        }
    }
}
