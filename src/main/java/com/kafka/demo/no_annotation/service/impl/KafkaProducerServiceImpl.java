//package com.kafka.demo.no_annotation.service.impl;
//
//import com.alibaba.fastjson.JSONObject;
//import com.kafka.demo.no_annotation.service.KafkaProducerService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class KafkaProducerServiceImpl implements KafkaProducerService {
//
//	private static final Logger logger = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);
//	@Autowired
//	private KafkaTemplate<Integer, String> kafkaTemplate;
//
//	public void sendDefaultInfo(String str) {
//		logger.info("----message--send----");
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("name", "张三");
//		jsonObject.put("age", 20);
//		jsonObject.put("address", "江苏南京");
//		kafkaTemplate.send("myDemo2", jsonObject.toString());
//	}
//
//}
