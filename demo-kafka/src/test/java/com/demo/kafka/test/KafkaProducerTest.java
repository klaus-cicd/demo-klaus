package com.demo.kafka.test;

import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootTest
public class KafkaProducerTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void testSend() {
        for (int i = 0; i < 5000; i++) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("datekey", 20210610);
            map.put("userid", i);
            map.put("salaryAmount", i);
            // 向kafka的big_data_topic主题推送数据
            kafkaTemplate.send("topic_test", JSONObject.toJSONString(map));
        }
    }

    @Test
    public void testBatch() {
        for (int i = 0; i < 5000; i++) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("datekey", 20210610);
            map.put("userid", i);
            map.put("salaryAmount", i);
            // 向kafka的big_data_topic主题推送数据
            kafkaTemplate.send("topic_test_batch", JSONObject.toJSONString(map));
        }
    }
}