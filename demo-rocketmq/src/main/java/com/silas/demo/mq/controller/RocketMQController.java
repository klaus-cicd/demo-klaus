package com.silas.demo.mq.controller;

import com.fd.web.response.Result;
import com.silas.demo.mq.entity.User;
import com.silas.demo.mq.producer.MQProducerService;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo/rocketmq")
public class RocketMQController {

    @Autowired
    private MQProducerService mqProducerService;

    @GetMapping("/send")
    public void send() {
        User user = User.getUser();
        mqProducerService.send(user);
    }
    
    @GetMapping("/send-tag")
    public Result<SendResult> sendTag() {
        SendResult sendResult = mqProducerService.sendTagMsg("带有tag的字符消息");
        return Result.ok(sendResult);
    }

}
