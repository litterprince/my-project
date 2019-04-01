package com.spring.zk.server;

import com.spring.common.annotation.RpcService;
import com.spring.zk.coder.RpcDecoder;
import com.spring.zk.coder.RpcEncoder;
import com.spring.zk.message.RpcRequest;
import com.spring.zk.message.RpcResponse;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

public class RpcServer implements ApplicationContextAware, InitializingBean {
    private String serverAddress;
    private ServiceRegistry serviceRegistry;
    // 存放接口名与服务对象之间的映射关系
    private Map<String, Object> handlerMap = new HashMap<>();

    public RpcServer(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcServer(String serverAddress, ServiceRegistry serviceRegistry) {
        this.serverAddress = serverAddress;
        this.serviceRegistry = serviceRegistry;
    }

    // TODO: 学习，在spring启动context的时候加载服务信息
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取所有带有 RpcService 注解的 Spring Bean
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(RpcService.class);
        // 放入map中存放
        if (MapUtils.isNotEmpty(beans)) {
            for (Object bean : beans.values()) {
                String key = bean.getClass().getAnnotation(RpcService.class).value().getName();
                handlerMap.put(key, bean);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // start server netty
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        try {
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new RpcEncoder(RpcRequest.class));
                            socketChannel.pipeline().addLast(new RpcDecoder(RpcResponse.class));
                            socketChannel.pipeline().addLast(new ServerHandler(handlerMap));
                        }
                    // TODO: 学习，设置客户端请求队列的大小
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            String host = "";
            int port = 0;
            if (StringUtils.isNoneBlank(serverAddress) && serverAddress.contains(":")) {
                host = serverAddress.split(":")[0];
                port = Integer.parseInt(serverAddress.split(":")[1]);
            }
            ChannelFuture future = b.bind(host, port).sync();
            System.out.println("rpc server start at port: " + port);

            // TODO: 学习，netty启动后注册服务
            if (serviceRegistry != null) {
                serviceRegistry.register(serverAddress);
            }

            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!bossGroup.isShutdown()) bossGroup.shutdownGracefully();
            if (!workerGroup.isShutdown()) workerGroup.shutdownGracefully();
        }
    }
}
