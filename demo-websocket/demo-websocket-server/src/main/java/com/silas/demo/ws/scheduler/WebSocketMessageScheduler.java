package com.silas.demo.ws.scheduler;

import com.silas.demo.ws.WebSocketManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Klaus
 */
@Slf4j
// @Component
public class WebSocketMessageScheduler {


    @Scheduled(cron = "0/30 * * * * ?")
    public void sendWebSocketMessage() {
        // 客户端订阅的目标
        String destination = "/topic/myTopic";
        // 要发送的消息
        String message = "Hello, client!";
        log.info("准备向所有WS客户端发送消息, msg:{}", message);
        WebSocketManager.sendAllMessage(message);
    }
}