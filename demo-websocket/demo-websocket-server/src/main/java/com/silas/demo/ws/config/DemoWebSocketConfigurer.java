package com.silas.demo.ws.config;

import com.silas.demo.ws.interceptor.WSAuthInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.Resource;

/**
 * @author Klaus
 */
@Slf4j
@Configuration
@EnableWebSocket
public class DemoWebSocketConfigurer {

    @Resource
    private WSAuthInterceptor wsAuthInterceptor;

    /**
     * 注入一个ServerEndpointExporter,该Bean会自动注册使用@ServerEndpoint注解申明的websocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


    // @Override
    // public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    //     registry.addHandler(new WSBaseHandler(), "/**")
    //             .addInterceptors(wsAuthInterceptor)
    //             // 允许跨域
    //             .setAllowedOrigins("sangoai.com")
    //             // SockJS 是一个 WebSocket 的替代方案，它提供了一种在不支持原生 WebSocket 的浏览器上使用类似 WebSocket 的实时通信机制的方法
    //             // SockJS 使用一种技术栈来模拟 WebSocket 功能，以便在不支持 WebSocket 的环境中提供类似的实时通信体验
    //             .withSockJS();
    // }
}
