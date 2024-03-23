package com.klaus.demo.netty.server.web;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.springframework.stereotype.Component;

@Sharable
@Component
public class WebsocketMessageHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) msg;

            // Reply to client
            ctx.channel().writeAndFlush(new TextWebSocketFrame("Received your message -> " + textWebSocketFrame.text()));
        } else {

            // Invalid message type
            ctx.channel().writeAndFlush(WebSocketCloseStatus.INVALID_MESSAGE_TYPE).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("WebsocketMessageHandler -> channelInactive(): " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("WebsocketMessageHandler -> channelActive(): " + ctx.channel().remoteAddress());
    }
}