package com.silas.demo.ws.server;

import lombok.Data;

import javax.websocket.Session;

/**
 * @author Klaus
 */
@Data
public abstract class AbstractWebSocketServer {

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    protected Session session;

    /**
     * 租户ID
     */
    protected String tenantId;

    /**
     * 接收userId
     */
    protected Long userId;
}
