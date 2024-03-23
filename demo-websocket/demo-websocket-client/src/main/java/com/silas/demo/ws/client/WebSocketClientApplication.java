package com.silas.demo.ws.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;

/**
 * @author Klaus
 */
@Slf4j
@SpringBootApplication
public class WebSocketClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketClientApplication.class, args);

        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new StringMessageConverter());

        // WebSocket 服务器端点 URL
        String url = "ws://localhost:8080/ws/silas/1234567";
        // 自定义的会话处理器
        // StompSessionHandlerAdapter sessionHandler = new MyStompSessionHandler();
        // stompClient.connect(url, sessionHandler);

        // new MyWebSocketClient().connect();
        new SgDemoMsgWSClient().connect();
        new SgMsgWSClient().connect();
    }

    private static class MyStompSessionHandler extends StompSessionHandlerAdapter {
        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            log.info("Connected to server");
            // 在连接建立后的回调方法中，您可以执行必要的操作

            // 订阅特定的目标
            session.subscribe("/topic/myTopic", new StompFrameHandler() {
                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return String.class;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    String message = (String) payload;
                    log.info("Received message: " + message);
                    // 处理收到的消息
                }
            });
        }
    }
}