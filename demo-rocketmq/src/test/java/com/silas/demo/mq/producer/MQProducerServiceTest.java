package com.silas.demo.mq.producer;

import com.silas.demo.mq.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MQProducerServiceTest {

    @Autowired
    private MQProducerService mqProducerService;

    @Test
    void send() {
        mqProducerService.send(User.getUser());
    }

    @Test
    void sendMsg() {
        mqProducerService.sendMsg("Test sendMsg");
    }

    @Test
    void sendAsyncMsg() {

        mqProducerService.sendAsyncMsg("Test sendAsyncMsg");
    }

    @Test
    void sendDelayMsg() {
        mqProducerService.sendDelayMsg("Test sendDelayMsg", 1);
    }

    @Test
    void sendOneWayMsg() {
        mqProducerService.sendOneWayMsg("Test sendOneWayMsg");
    }

    @Test
    void sendTagMsg() {
        mqProducerService.sendTagMsg("Test sendTagMsg");
    }
}