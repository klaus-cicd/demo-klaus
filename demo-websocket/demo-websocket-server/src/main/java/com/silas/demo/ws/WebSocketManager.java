package com.silas.demo.ws;

import com.silas.demo.ws.server.DemoWebSocketServer;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Klaus
 */
@Slf4j
public class WebSocketManager {

    private static final CopyOnWriteArraySet<DemoWebSocketServer> WEB_SOCKET_SERVERS = new CopyOnWriteArraySet<>();

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

    public synchronized static void addWebSocket(DemoWebSocketServer demoWebSocketServer) {
        WEB_SOCKET_SERVERS.add(demoWebSocketServer);
    }


    public static Set<DemoWebSocketServer> allWebSocket() {
        return WEB_SOCKET_SERVERS;
    }

    public synchronized static void remove(DemoWebSocketServer demoWebSocketServer) {
        try {
            demoWebSocketServer.getSession().close();
        } catch (IOException e) {
            log.error("WebSocketManager#remove webSocketServer error, sessionId:{}, tenantId:{}, userId:{}",
                    demoWebSocketServer.getSession(), demoWebSocketServer.getTenantId(), demoWebSocketServer.getUserId(), e);
        } finally {
            WEB_SOCKET_SERVERS.remove(demoWebSocketServer);
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
     * 单点消息
     *
     * @param userId
     * @param message
     */
    public static void sendOneMessage(Long userId, String message) {
        Session session = SESSION_POOL.get(userId);
        if (session != null && session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:" + message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                log.error("WebSocketManager#sendMoreMessage error, userId:{}, msg:{}", userId, message);
            }
        }
    }

    /**
     * 此为单点消息(多人)
     *
     * @param userIds
     * @param message
     */
    public static void sendMoreMessage(Long[] userIds, String message) {
        for (Long userId : userIds) {
            Session session = SESSION_POOL.get(userId);
            if (session != null && session.isOpen()) {
                try {
                    log.info("【websocket消息】 单点消息:" + message);
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
     * @param message
     */
    public static void sendAllMessage(String message) {
        log.info("【websocket消息】广播消息:" + message);
        for (DemoWebSocketServer webSocket : WEB_SOCKET_SERVERS) {
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
