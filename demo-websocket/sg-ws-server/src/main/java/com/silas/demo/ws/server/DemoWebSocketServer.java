package com.silas.demo.ws.server;

import com.silas.demo.ws.WebSocketManager;
import com.silas.demo.ws.config.WebSocketAuthConfigurator;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@ServerEndpoint(value = "/ws/msg/2", configurator = WebSocketAuthConfigurator.class, subprotocols = {"protocol"})
public class DemoWebSocketServer extends AbstractWebSocketServer {


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException {
        log.info("WebSocket 同客户端建立连接成功!sessionId:{}, tenantId:{}, userId:{}", session.getId(), tenantId, userId);
        // this.tenantId = tenantId;
        // this.userId = userId;
        this.session = session;
        // WebSocketManager.addWebSocket(this);
        // WebSocketManager.addSession(userId, session);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        printThread();
        try {
            WebSocketManager.remove(this.userId);
            WebSocketManager.remove(this);
            log.info("【websocket消息】连接断开，总数为:" + WebSocketManager.webSocketSize());
        } catch (Exception e) {
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        printThread();
        log.info("收到消息来自窗口: tenantId:{}, userId={}, message={}, sessionId={}", this.tenantId, this.userId, message, session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        printThread();

        log.error("发生错误: userId={}, sessionId={}", this.userId, session.getId(), error);
    }


    private void printThread() {
        Thread thread = Thread.currentThread();
        ThreadGroup threadGroup = thread.getThreadGroup();
        log.info("=================[thread={},threadGroup={},threadActiveInGroup={}]==================", thread.getName(), threadGroup.getName(), threadGroup.activeCount());
    }
}