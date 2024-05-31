package com.demo.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 大数据主题监听器
 *
 * @author klaus
 * @date 2024/05/31
 */
@Slf4j
@Component
public class BigDataTopicListener {


    /**
     * 监听kafka数据
     *
     * @param consumerRecord 消费记录
     */
    @KafkaListener(topics = {"topic_test"})
    public void consumer(ConsumerRecord<?, ?> consumerRecord) {
        log.info("收到来自topic_test 的数据'{}'", consumerRecord.toString());
        //...
        // db.save(consumerRecord);//插入或者更新数据
        // ack.acknowledge();
    }

    /**
     * 监听kafka数据（批量消费）
     *
     * @param consumerRecords
     * @param ack             消
     */
    @KafkaListener(topics = {"topic_test_batch"}, containerFactory = "batchFactory")
    public void batchConsumer(List<ConsumerRecord<?, ?>> consumerRecords, Acknowledgment ack) {
        long start = System.currentTimeMillis();

        //...
        // db.batchSave(consumerRecords);//批量插入或者批量更新数据

        // 手动提交
        ack.acknowledge();
        log.info("收到批量数据，拉取数据量：{}，消费时间：{}ms", consumerRecords.size(), (System.currentTimeMillis() - start));
    }

}