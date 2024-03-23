package com.silas.dsdemo.controller;


import com.fd.web.response.Result;
import com.silas.dsdemo.constants.MQConstant;
import com.silas.dsdemo.entity.DS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("ds")
public class DSController {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public DSController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public Result<Void> addDS(@RequestBody DS ds) {
        // TODO 新建数据库并做出相关初始化

        // MQ通知所有节点执行添加数据源操作
        rabbitTemplate.convertAndSend(MQConstant.EXCHANGE_NAME, "", ds);
        log.info("数据源添加MQ发送成功, db:{}", ds.getDb());
        return Result.ok();
    }
}
