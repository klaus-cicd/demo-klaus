package com.demo.rabbitmq.consumer;

import com.demo.rabbitmq.constant.MQConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 异步POST请求消费
 * (使用MQ时为了方便失败重试)
 *
 * @author Klaus
 */
@Slf4j
@Component
public class DemoMQConsumer {

    @RabbitListener(
            bindings = @QueueBinding(
                    exchange = @Exchange(value = MQConstant.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = MQConstant.RETRY_ROUTING_KEY,
                    value = @Queue(MQConstant.QUEUE_A)
            ),
            ackMode = "MANUAL")
    public void topic(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliverTag) throws IOException {
        log.info("HttpPostReqMQConsumer#topic msg:{} channel:{}", message, channel);
        channel.basicAck(deliverTag, true);
    }

    /**
     * 广播-指定队列 适合不同代码之间的广播
     *
     * @param message
     * @param channel
     * @param deliverTag
     * @throws IOException
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    exchange = @Exchange(value = MQConstant.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT),
                    key = MQConstant.RETRY_ROUTING_KEY,
                    value = @Queue(MQConstant.QUEUE_B)
            ),
            ackMode = "MANUAL")
    public void fanoutSpecifiedQueue(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliverTag) throws IOException {
        log.info("HttpPostReqMQConsumer#fanoutSpecifiedQueue msg:{} channel:{}", message, channel);
        channel.basicAck(deliverTag, true);
    }

    /**
     * 广播-动态队列(即接收所有队列的消息, 适合一份代码多节点的情况部署)
     *
     * @param message
     * @param channel
     * @param deliverTag
     * @throws IOException
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(),
                    exchange = @Exchange(value = MQConstant.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT)
            ),
            ackMode = "MANUAL")
    public void fanoutDynamicQueue(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliverTag) throws IOException {
        log.info("HttpPostReqMQConsumer#fanoutDynamicQueue msg:{} channel:{}", message, channel);
        channel.basicAck(deliverTag, true);
    }
}
