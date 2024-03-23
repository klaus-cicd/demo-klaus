package com.silas.demo.ws.server;

import com.silas.demo.ws.WebSocketManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class DemoWebSocketServerTest {

    @Resource
    private DemoWebSocketServer demoWebSocketServer;
    @Test
    void sendOneMessage() {
    }

    @Test
    void sendMoreMessage() {
    }

    @Test
    void sendAllMessage() {
        // 客户端订阅的目标
        String destination = "/topic/myTopic";
        // 要发送的消息
        String message = "Hello, client!";
        log.info("准备向所有WS客户端发送消息, msg:{}", message);
        WebSocketManager.sendAllMessage(message);
    }
}