package com.demo.rabbitmq.producer;

import com.demo.rabbitmq.constant.MQConstant;
import com.klaus.fd.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Klaus
 */
@Component
@RequiredArgsConstructor
public class DelayMsgProducer {

    private final RabbitTemplate rabbitTemplate;


    public void testSendDelayMsg() {
        sendDelayMsg(MQConstant.DELAY_MSG_TOPIC, DateUtil.now().toString(), 3L * 1000);
    }


    /**
     * 发送延时消息
     *
     * @param topic            Topic
     * @param msg              消息内容
     * @param delayMillisecond 延时时间(ms)
     */
    public void sendDelayMsg(String topic, String msg, Long delayMillisecond) {
        rabbitTemplate.convertAndSend(MQConstant.DELAY_EXCHANGE, topic, msg, message -> {
            // 设置延迟时间
            message.getMessageProperties().setHeader("x-delay", delayMillisecond);
            return message;
        });
    }


}
