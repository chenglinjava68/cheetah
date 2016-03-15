package cheetah.fighter.fighter.server.mechanism;

import cheetah.fighter.commons.utils.Assert;
import cheetah.fighter.fighter.core.mechanism.DispatcherMessage;
import cheetah.fighter.fighter.protocol.protobuf.ProtocolConvertor;
import cheetah.fighter.fighter.server.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Max on 2016/3/13.
 */
public class DefaultBootstrap implements Bootstrap {
    private EventLoopGroup serverBossGroup;
    private EventLoopGroup serverWorkGroup;
    private int port;
    private final List<ChannelHandler> channelHandlers;

    public DefaultBootstrap() {
        this.port = 9019;
        this.channelHandlers = new ArrayList<>();
    }

    @Override
    public void initialize() {
        int processors = Runtime.getRuntime().availableProcessors();
        serverBossGroup = new NioEventLoopGroup(processors);
        serverWorkGroup = new NioEventLoopGroup((int) (processors / (1 - 0.6)));
    }

    @Override
    public void registerHandler(List<ChannelHandler> $channelHandlers) {
        Assert.notNull($channelHandlers);
        channelHandlers.addAll($channelHandlers);
    }

    @Override
    public void registerHandler(ChannelHandler channelHandler) {
        Assert.notNull(channelHandler);
        channelHandlers.add(channelHandler);
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void start() {
        try {
            new ServerBootstrap()
                    .group(serverBossGroup, serverWorkGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.ERROR))
                    .childHandler(generateChannelInitializer())
                            //                .option(ChannelOption.SO_REUSEADDR, tcpRecycle)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .bind(port)
                    .sync();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private ChannelInitializer<Channel> generateChannelInitializer() {
        return new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                if (channelHandlers.isEmpty()) {
                    channelHandlers.add(new IdleStateHandler(0, 0, 5));
                    channelHandlers.add(new ProtobufVarint32FrameDecoder());
                    channelHandlers.add(new ProtobufDecoder(ProtocolConvertor.Protocol.getDefaultInstance()));
                    channelHandlers.add(new ProtobufVarint32LengthFieldPrepender());
                    channelHandlers.add(new ProtobufEncoder());
                    channelHandlers.add(new SessionIdleStateHandler());
                    channelHandlers.add(new DispatcherMessage());
                }
                channelHandlers.forEach(o -> channel.pipeline().addLast(o));
            }
        };
    }

    @Override
    public void stop() {
        if (Objects.nonNull(serverBossGroup))
            serverBossGroup.shutdownGracefully();
        if (Objects.nonNull(serverWorkGroup))
            serverWorkGroup.shutdownGracefully();
    }

    public final int port() {
        return port;
    }
}
