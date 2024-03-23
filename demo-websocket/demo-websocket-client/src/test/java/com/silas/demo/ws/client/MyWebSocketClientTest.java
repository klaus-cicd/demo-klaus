package com.silas.demo.ws.client;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MyWebSocketClientTest {

    @Resource
    private MyWebSocketClient myWebSocketClient;

    @Test
    void connect() {
        myWebSocketClient.connect();
    }
}