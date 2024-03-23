package com.silas.demo.ws.client;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Component
public class SgMsgWSClient {

    public void connect() {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(client, getHandler(), "ws://localhost:8107/ws/msg");

        // 创建自定义请求头，并设置 Token
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer YOUR_TOKEN_HERE");

        // 将自定义请求头添加到连接管理器中
        connectionManager.setHeaders(headers);

        connectionManager.start();
    }

    private WebSocketHandler getHandler() {
        return new WebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                System.out.println("Connected to server");
                // 在连接建立后的回调方法中，您可以执行必要的操作
            }

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
                System.out.println("Received message: " + message.getPayload());
                // 处理收到的消息
            }

            @Override
            public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
                System.out.println("Connection error");
                // 处理连接错误
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                System.out.println("Connection closed");
                // 处理连接关闭
            }

            @Override
            public boolean supportsPartialMessages() {
                return false;
            }
        };
    }
}