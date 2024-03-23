package com.demo.rabbitmq.producer;

import com.alibaba.fastjson2.JSON;
import com.demo.rabbitmq.constant.MQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author ak
 * @date 2023/3/29 14:58
 * @desc
 */
@Slf4j
@Component
public class DemoProducer {

    private final RabbitTemplate rabbitTemplate;

    public DemoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToTopicExchange(String routingKey, Object data) {
        rabbitTemplate.convertAndSend(MQConstant.TOPIC_EXCHANGE, routingKey, JSON.toJSONString(data).getBytes(StandardCharsets.UTF_8));
    }

    public void sendToFanoutExchange(Object data) {
        rabbitTemplate.convertAndSend(MQConstant.FANOUT_EXCHANGE, "", JSON.toJSONString(data).getBytes(StandardCharsets.UTF_8));
    }
}
