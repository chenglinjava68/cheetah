package cheetah.predator.server;

import cheetah.commons.Startable;
import cheetah.predator.transport.SessionTransportConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.List;
import java.util.Objects;

/**
 * Created by Max on 2016/3/13.
 */
public interface Bootstrap extends Startable {
    String IO_RATIO = "io.ratio";
    String TCP_BACKLOG = "tcp.backlog";
    String TCP_RECYCLE = "tcp.recycle";
    int DEFAULT_TCP_BACKLOG = 9224;
    boolean DEFAULT_TCP_RECYCLE = false;

    @Override
    default void start() {
        try {
            if (serverBossGroup() == null || serverWorkGroup() == null)
                initialize();
            int tcpBacklog = transportConfig().intParamOrDefault(TCP_BACKLOG, DEFAULT_TCP_BACKLOG);
            boolean tcpRecycle = transportConfig().booleanParamOrDefault(TCP_RECYCLE, DEFAULT_TCP_RECYCLE);
            new ServerBootstrap()
                    .group(serverBossGroup(), serverWorkGroup())
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.ERROR))
                    .childHandler(generateChannelInitializer())
                    .option(ChannelOption.SO_REUSEADDR, tcpRecycle)
                    .option(ChannelOption.SO_BACKLOG, tcpBacklog)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .bind(getPort())
                    .sync();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
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

    void registerHandler(List<ChannelHandler> channelHandlers);

    void setHandlers(List<ChannelHandler> channelHandlers);

    void registerHandler(ChannelHandler channelHandler);

    int getPort();

    EventLoopGroup serverBossGroup();

    EventLoopGroup serverWorkGroup();

    ChannelInitializer<Channel> generateChannelInitializer();

    void setTransportConfig(SessionTransportConfig transportConfig);

    SessionTransportConfig transportConfig();

}
