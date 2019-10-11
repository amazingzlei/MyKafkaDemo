package com.kafka.demo.annotation;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class KafkaAnnotationController {
    private static final Logger logger = LoggerFactory.getLogger(KafkaAnnotationController.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/testAnnotation")
    public String test(){
        logger.info("****************发送Kafka信息***************");
        System.err.println("---------发送失败---------");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "张三");
        jsonObject.put("age", 50);
        jsonObject.put("address", "唱、跳、rap");
        kafkaTemplate.send("myDemo2",jsonObject.toString());
        logger.info("***************发送结束*****************************");
        return "index";
    }
}
