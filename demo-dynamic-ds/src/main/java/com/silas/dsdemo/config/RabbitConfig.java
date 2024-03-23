package com.silas.dsdemo.config;

import com.silas.dsdemo.constants.MQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    /**
     * 创建交换机
     *
     * @return Exchange
     */
    @Bean("ddsFanoutExchange")
    public Exchange fanoutExchange() {
        log.info("====== Create bean ddsFanoutExchange ======");
        return ExchangeBuilder.fanoutExchange(MQConstant.EXCHANGE_NAME)
                .durable(false)
                .autoDelete()
                .build();
    }

    /**
     * 创建队列
     *
     * @return
     */
    @Bean("bootQueue")
    public Queue getMessageQueue() {
        return new Queue(MQConstant.QUEUE_NAME, false, false, true);
    }

    /**
     * 交换机绑定队列
     *
     * @param fanoutExchange
     * @param queue
     * @return
     */
    @Bean
    public Binding bindMessageQueue(@Qualifier("ddsFanoutExchange") Exchange fanoutExchange, @Qualifier("bootQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(fanoutExchange).with("").noargs();
    }
}
