package com.silas.demo.ws.controller;

import com.silas.demo.ws.WebSocketManager;
import com.silas.demo.ws.server.DemoWebSocketServer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Klaus
 */
@RestController
public class WSController {
    @Resource
    private DemoWebSocketServer demoWebSocketServer;

    @SendTo
    @MessageMapping("/send")
    @RequestMapping("/api/ws/send-all")
    public String sendMsg(String msg) {
        WebSocketManager.sendAllMessage(msg);
        return "接收到消息: " + msg;
    }


}
