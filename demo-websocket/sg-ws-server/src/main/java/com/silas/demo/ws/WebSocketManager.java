package com.silas.demo.ws;

import com.silas.demo.ws.server.AbstractWebSocketServer;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocket连接管理工具
 * 集群部署时不可单独使用, 需要配置MQ广播使用
 *
 * @author Klaus
 */
@Slf4j
public class WebSocketManager {

    private static final CopyOnWriteArraySet<AbstractWebSocketServer> WEB_SOCKET_SERVERS = new CopyOnWriteArraySet<>();

    /**
     * 存储WebSocket session信息
     * Key: userId
     * Value: Session对象
     */
    private static final ConcurrentHashMap<Long, Session> SESSION_POOL = new ConcurrentHashMap<>();


    public static Session getSessionByUserId(Long userId) {
        return SESSION_POOL.get(userId);
    }

    public synchronized static void addSession(Long userId, Session session) {
        SESSION_POOL.put(userId, session);
    }

    public synchronized static void addWebSocket(AbstractWebSocketServer webSocketServer) {
        WEB_SOCKET_SERVERS.add(webSocketServer);
    }


    public static Set<AbstractWebSocketServer> allWebSocket() {
        return WEB_SOCKET_SERVERS;
    }

    public synchronized  static void remove(AbstractWebSocketServer webSocketServer) {
        try {
            webSocketServer.getSession().close();
        } catch (IOException e) {
            log.error("WebSocketManager#remove webSocketServer error, sessionId:{}, tenantId:{}, userId:{}",
                    webSocketServer.getSession(), webSocketServer.getTenantId(), webSocketServer.getUserId(), e);
        } finally {
            WEB_SOCKET_SERVERS.remove(webSocketServer);
        }
    }

    public synchronized static void remove(Long userId) {
        Session session = SESSION_POOL.get(userId);
        try {
            session.close();
            SESSION_POOL.remove(userId);
        } catch (IOException e) {
            log.error("WebSocketManager#remove error, userId:{}", userId, e);
        }
    }

    public static Integer webSocketSize() {
        return WEB_SOCKET_SERVERS.size();
    }

    /**
     * 发送单个消息
     *
     * @param userId  用户ID
     * @param message 消息内容
     */
    public static void sendMsgByUserId(Long userId, String message) {
        Session session = SESSION_POOL.get(userId);
        if (session != null && session.isOpen()) {
            try {
                log.info("【WebSocket message】 userId:{}, msg:{}", userId, message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                log.error("WebSocketManager#sendMoreMessage error, userId:{}, msg:{}", userId, message);
            }
        }
    }

    /**
     * 此为单点消息(多人)
     *
     * @param userIds 发送目标用户ID
     * @param message 文本消息内容
     */
    public static void sendMsgByUserIds(Long[] userIds, String message) {
        for (Long userId : userIds) {
            Session session = SESSION_POOL.get(userId);
            if (session != null && session.isOpen()) {
                try {
                    log.info("【WebSocket message】 batch, userIds:{}, msg:{} ", userIds, message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    log.error("WebSocketManager#sendMoreMessage error, userIds:{}, msg:{}", userIds, message);
                }
            }
        }
    }

    /**
     * 广播消息
     *
     * @param message 文本消息内容
     */
    public static void sendAllMessage(String message) {
        log.info("【WebSocket message】broadcast: {}", message);
        for (AbstractWebSocketServer webSocket : WEB_SOCKET_SERVERS) {
            try {
                if (webSocket.getSession().isOpen()) {
                    webSocket.getSession().getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                log.error("WebSocketManager#sendAllMessage error, sessionId:{}, tenantId:{}, userId:{}",
                        webSocket.getSession(), webSocket.getTenantId(), webSocket.getUserId(), e);
            }
        }
    }
}
