package com.demo.rabbitmq.consumer;

import com.demo.rabbitmq.constant.MQConstant;
import com.klaus.fd.utils.DateUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 使用RabbitMQ的延时队列, 需要安装rabbitmq_delayed_message_exchange插件
 * @author Klaus
 */
@Slf4j
@Component
public class DelayMsgConsumer {

    // 方式一: 手动声明队列和交换机
    // @Bean(MQConstant.DELAY_MSG_QUEUE)
    // public org.springframework.amqp.core.Queue delayedQueue() {
    //     return new org.springframework.amqp.core.Queue(MQConstant.DELAY_MSG_QUEUE, true);
    // }

    // 自定义交换机，再这里定义的是一个延迟交换机
    // @Bean(MQConstant.DELAY_EXCHANGE)
    // public CustomExchange delayedExchange() {
    //     Map<String, Object> map = new HashMap<>(1);
    //     // 自定义交换机的类型
    //     map.put("x-delayed-type", "direct");
    //     return new CustomExchange(MQConstant.DELAY_EXCHANGE, "x-delayed-message", true, false, map);
    // }

    // @Bean
    // public Binding bindingDelayedQueue(@Qualifier(MQConstant.DELAY_EXCHANGE) CustomExchange delayedExchange,
    //                                    @Qualifier(MQConstant.DELAY_MSG_QUEUE) org.springframework.amqp.core.Queue queue) {
    //     return BindingBuilder.bind(queue).to(delayedExchange).with(MQConstant.DELAY_MSG_TOPIC).noargs();
    // }

    // @RabbitListener(queues = MQConstant.DELAY_MSG_QUEUE)

    // 方式二: 注解式
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = MQConstant.DELAY_EXCHANGE, delayed = "true",
                    arguments = {@Argument(name = "x-delayed-type", value = "direct")}
            ),
            key = MQConstant.DELAY_MSG_TOPIC,
            value = @Queue(value = MQConstant.DELAY_MSG_QUEUE, arguments = {
                    @Argument(name = "x-max-length", value = "100000", type = "java.lang.Integer")
            })
    ), ackMode = "AUTO")
    public void buzzerConsumer(Channel channel, Message message) {
        String msgBody = new String(message.getBody());
        log.info("收到延时消息, time: {}, message: {}", DateUtil.now(), msgBody);
    }

}
