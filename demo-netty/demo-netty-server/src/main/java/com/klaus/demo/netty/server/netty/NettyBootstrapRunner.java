package com.klaus.demo.netty.server.netty;

import com.klaus.demo.netty.server.web.WebsocketMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

import java.net.InetSocketAddress;

/**
 * @author Klaus
 */
@Slf4j
@Configuration
public class NettyBootstrapRunner implements ApplicationRunner, ApplicationListener<ContextClosedEvent>, ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Value("${netty.websocket.port}")
    private int port;

    @Value("${netty.websocket.ip}")
    private String ip;

    @Value("${netty.websocket.path}")
    private String path;

    @Value("${netty.websocket.max-frame-size}")
    private long maxFrameSize;


    private Channel serverChannel;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 创建2个NIO线程池, 一般来说，bossGroup 的线程池大小可以设置为 1 到 2 倍 CPU 核数。workerGroup 的线程池大小可以设置为 CPU 核数的 2 到 4 倍

        // 设置BossGroup组, 负责监听端口 等待客户端连接请求; 接受客户端连接请求，并将其分配给 workerGroup 中的线程进行处理
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        // 处理客户端连接的数据读写操作;处理来自客户端的消息，并将其传递给相应的业务逻辑处理程序;向客户端发送消息
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        try {
            // 用于创建和启动服务器端:
            // 1. 配置服务器端参数，例如端口号、连接超时时间、线程池大小等;
            // 2. 设置 ChannelInitializer，用于初始化每个连接的 ChannelPipeline
            // 3. 绑定服务器端到指定的端口
            // 4. 启动服务器端
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 设置parentGroup和childGroup, 只能有一个childGroup
            serverBootstrap.group(bossGroup, workerGroup);
            // 设置服务器端 Channel 的类型为 NioServerSocketChannel。NioServerSocketChannel 是 NIO 方式的服务器端 Channel 的实现类
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 设置本server的host:port
            serverBootstrap.localAddress(new InetSocketAddress(this.ip, this.port));
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new HttpServerCodec());
                    pipeline.addLast(new ChunkedWriteHandler());
                    pipeline.addLast(new HttpObjectAggregator(65536));
                    pipeline.addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            if (msg instanceof FullHttpRequest) {
                                FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
                                String uri = fullHttpRequest.uri();
                                if (!uri.equals(path)) {
                                    // 访问的路径不是 websocket的端点地址，响应404
                                    ctx.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND))
                                            .addListener(ChannelFutureListener.CLOSE);
                                    return;
                                }
                            }
                            super.channelRead(ctx, msg);
                        }
                    });
                    pipeline.addLast(new WebSocketServerCompressionHandler());
                    pipeline.addLast(new WebSocketServerProtocolHandler(path, null, true, maxFrameSize));

                    /**
                     * 从IOC中获取到Handler
                     */
                    pipeline.addLast(applicationContext.getBean(WebsocketMessageHandler.class));
                }
            });
            Channel channel = serverBootstrap.bind().sync().channel();
            this.serverChannel = channel;
            log.info("websocket 服务启动，ip={},port={}", this.ip, this.port);
            channel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {

    }
}
