package com.silas.demo.ws.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * WS基础拦截器
 *
 * @author Klaus
 */
@Slf4j
public class WSBaseHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("WS establish connection success! SessionId:{}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 处理接收到的WebSocket消息
        String payload = message.getPayload();
        log.info("接收到消息：" + payload);

        // 发送回复消息给客户端
        String replyMessage = "收到消息：" + payload;
        session.sendMessage(new TextMessage(replyMessage));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WS connect error! SessionId:{}", session.getId(), exception);
    }
}
