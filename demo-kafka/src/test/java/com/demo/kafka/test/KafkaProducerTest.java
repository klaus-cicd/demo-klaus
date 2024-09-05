package com.demo.kafka.test;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
public class KafkaProducerTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 不带回调函数的生产者测试
     */
    @Test
    public void testSendWithoutCallback() {
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


    @Test
    public void testSendWithCallback1() {
        kafkaTemplate.send("topic_cb1", "callbackMessage1").addCallback(success -> {
            // 消息发送到的topic
            String topic = success.getRecordMetadata().topic();
            // 消息发送到的分区
            int partition = success.getRecordMetadata().partition();
            // 消息在分区内的offset
            long offset = success.getRecordMetadata().offset();
            log.info("发送消息成功:{}-{}-{}", topic, partition, offset);
        }, failure -> {
            log.error("发送消息失败:{}", failure.getMessage());
        });
    }

    @Test
    public void testSendWithCallback2() {
        kafkaTemplate.send("topic_cb2", "callbackMessage2")
                .addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                    @Override
                    public void onSuccess(SendResult<String, String> result) {
                        System.out.println("发送消息成功：" + result.getRecordMetadata().topic() + "-"
                                + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        System.out.println("发送消息失败：" + ex.getMessage());
                    }
                });
    }
}