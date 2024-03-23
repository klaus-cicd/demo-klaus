package com.silas.dsdemo.mq;

import com.alibaba.fastjson2.JSON;
import com.rabbitmq.client.Channel;
import com.silas.dsdemo.constants.MQConstant;
import com.silas.dsdemo.entity.DS;
import com.silas.dsdemo.service.DynamicManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RabbitListener(bindings = @QueueBinding(value = @Queue(),
        exchange = @Exchange(value = MQConstant.EXCHANGE_NAME, type = ExchangeTypes.FANOUT, durable = "false", autoDelete = "true")),
        ackMode = "MANUAL")
public class DynamicMQConsumer {

    private final DynamicManagerService dynamicManagerService;

    @Autowired
    public DynamicMQConsumer(DynamicManagerService dynamicManagerService) {
        this.dynamicManagerService = dynamicManagerService;
    }

    @RabbitHandler
    public void listenMessage(DS ds, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        if (ds != null && dynamicManagerService.addDynamicDS(ds)) {
            channel.basicAck(tag, false);
            log.info("动态数据源新增消费成功, DB:{}, Channel:{}", ds.getDb(), JSON.toJSONString(channel));
        } else {
            channel.basicNack(tag, false, false);
            log.error("动态数据源新增消费失败, Channel:{}", JSON.toJSONString(channel));
        }
    }
}
