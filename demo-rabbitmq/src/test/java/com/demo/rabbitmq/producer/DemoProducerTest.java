package com.demo.rabbitmq.producer;

import cn.hutool.core.util.StrUtil;
import com.demo.rabbitmq.constant.MQConstant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoProducerTest {

    @Autowired
    private DemoProducer demoProducer;

    @Test
    void sendToRetryExchange() {
        demoProducer.sendToTopicExchange(StrUtil.format(MQConstant.RETRY_ROUTING_KEY_PREFIX, "123"), "{}");
    }

    @Test
    void sendToFanoutExchange() {
        demoProducer.sendToFanoutExchange("{}");
    }
}