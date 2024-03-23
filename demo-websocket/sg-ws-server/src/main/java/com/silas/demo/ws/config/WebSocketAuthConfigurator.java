package com.silas.demo.ws.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;

/**
 * @author Klaus
 */
@Slf4j
public class WebSocketAuthConfigurator extends ServerEndpointConfig.Configurator {



    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        // 在这里实现您的鉴权逻辑
        // 您可以从 HandshakeRequest 中获取请求的信息，进行鉴权判断
        // 如果鉴权失败，可以抛出异常或设置响应状态码等

        // 示例：根据请求的参数进行鉴权
        // List<String> tokenList = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
        List<String> protocolList = request.getHeaders().get("Sec-WebSocket-Protocol");
        if (CollectionUtils.isEmpty(protocolList)) {
            return;
        }
        String token = protocolList.get(0);
        // if (!isValidToken(token)) {
        //     // 鉴权失败，可以抛出异常或设置响应状态码等
        //     throw new SecurityException("Invalid token");
        // }
        //
        //  鉴权通过，可以在 ServerEndpointConfig 中设置一些属性，以便在 WebSocket 端点类中获取
        sec.getUserProperties().put("userId", 123456L);
        sec.getUserProperties().put("tenantId", "silas");
    }

    private boolean isValidToken(String token) {
        // 根据您的鉴权逻辑判断 Token 的有效性
        // 返回 true 表示鉴权通过，返回 false 表示鉴权失败
        log.info("WebSocketAuthConfigurator#isValidToken token:{}", token);
        return true;
    }

    private String getUserIdFromToken(String token) {
        // 根据 Token 获取用户 ID
        // 这里仅作示例，实际情况需要根据您的业务逻辑进行处理
        return "user123";
    }

    private String getTenantIdFromToken(String token) {
        // 根据 Token 获取用户 ID
        // 这里仅作示例，实际情况需要根据您的业务逻辑进行处理
        return "tenantId123";
    }
}