package com.silas.demo.ws.server;

import com.silas.demo.ws.WebSocketManager;
import com.silas.demo.ws.config.WebSocketAuthConfigurator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author Klaus
 */
@Slf4j
@Component
@ServerEndpoint(
        value = "/ws/msg",
        configurator = WebSocketAuthConfigurator.class,
        subprotocols = {"protocol"}
)
public class WebSocketServer extends AbstractWebSocketServer {

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 接收userId
     */
    private Long userId;


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException {
        // 从配置中获取属性
        Long userId = (Long) session.getUserProperties().get("userId");
        String tenantId = (String) config.getUserProperties().get("tenantId");

        this.tenantId = tenantId;
        this.userId = userId;
        this.session = session;
        WebSocketManager.addWebSocket(this);
        WebSocketManager.addSession(userId, session);

        log.info("【WebSocketServer】 客户端请求建立连接成功! sessionId:{}, tenantId:{}, userId:{}", session.getId(), tenantId, userId);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            WebSocketManager.remove(this);
            WebSocketManager.remove(this.userId);
            log.info("【WebSocketServer】#onClose 连接断开，剩余连接数:" + WebSocketManager.webSocketSize());
        } catch (Exception e) {
            log.error("【WebSocketServer】#onClose 断开连接异常", e);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("【WebSocketServer】 Receive ws: tenantId:{}, userId={}, message={}, sessionId={}", this.tenantId, this.userId, message, session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("【WebSocketServer】 error: userId={}, sessionId={}", this.userId, session.getId(), error);
    }

}