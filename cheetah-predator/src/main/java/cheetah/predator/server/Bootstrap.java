package cheetah.predator.server;

import cheetah.commons.Startable;
import cheetah.predator.core.support.SessionTransportConfig;
import io.netty.bootstrap.ServerBootstrap;
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
    String LOG_LEVEL = "log.level";
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
                            //容量动态调整的接收缓冲区分配器，它会根据之前Channel接收到的数据报大小进行计算，
                            // 如果连续填充满接收缓冲区的可写空间，则动态扩展容量。如果连续2次接收到的数据报都小于指定值，则收缩当前的容量，以节约内存
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
//                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new LoggingHandler(logLevel()))
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

    default LogLevel logLevel() {
        String logLevel = transportConfig().paramOrDefault(LOG_LEVEL, "WARN");
        switch (logLevel) {
            case "ERROR":
                return LogLevel.ERROR;
            case "WARN":
                return LogLevel.WARN;
            case "TRACE":
                return LogLevel.TRACE;
            case "DEBUG":
                return LogLevel.DEBUG;
            case "INFO":
                return LogLevel.INFO;
            default:
                return LogLevel.WARN;
        }
    }

    void initialize();

    int getPort();

    void setPipelineCrowd(PipelineCrowd pipelineCrowd);

    ChannelInitializer<Channel> ChannelCrowd();

    EventLoopGroup serverBossGroup();

    EventLoopGroup serverWorkGroup();

    void setTransportConfig(SessionTransportConfig transportConfig);

    SessionTransportConfig transportConfig();

}
