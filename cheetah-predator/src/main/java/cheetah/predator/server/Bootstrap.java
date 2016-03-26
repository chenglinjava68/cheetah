package cheetah.predator.server;

import cheetah.commons.Startable;
import cheetah.predator.core.support.SessionTransportConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Objects;

/**
 * Created by Max on 2016/3/13.
 */
public interface Bootstrap extends Startable {
    String IO_RATIO = "io.ratio";
    String TCP_BACKLOG = "tcp.backlog";
    String TCP_RECYCLE = "tcp.recycle";
    int DEFAULT_TCP_BACKLOG = 1024;
    boolean DEFAULT_TCP_RECYCLE = false;

    @Override
    default void start() {
        try {
            initialize();
            int tcpBacklog = transportConfig().intParamOrDefault(TCP_BACKLOG, DEFAULT_TCP_BACKLOG);
            boolean tcpRecycle = transportConfig().booleanParamOrDefault(TCP_RECYCLE, DEFAULT_TCP_RECYCLE);
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(serverBossGroup(), serverWorkGroup())
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_REUSEADDR, tcpRecycle)
                    .option(ChannelOption.SO_BACKLOG, tcpBacklog)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new LoggingHandler(LogLevel.ERROR))
                    .childHandler(ChannelCrowd());
            ChannelFuture future = bootstrap.bind(getPort()).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放线程池资源
            serverBossGroup().shutdownGracefully();
            serverWorkGroup().shutdownGracefully();
        }
    }

    @Override
    default void stop() {
        if (Objects.nonNull(serverBossGroup()))
            serverBossGroup().shutdownGracefully();
        if (Objects.nonNull(serverWorkGroup()))
            serverWorkGroup().shutdownGracefully();
    }

    void initialize();

    int getPort();

    void setChannelCrowd(ChannelCrowd channelCrowd);

    ChannelInitializer<Channel> ChannelCrowd();

    EventLoopGroup serverBossGroup();

    EventLoopGroup serverWorkGroup();

    void setTransportConfig(SessionTransportConfig transportConfig);

    SessionTransportConfig transportConfig();

}
