package cheetah.predator.server.support;

import cheetah.commons.utils.Assert;
import cheetah.fighter.core.Interceptor;
import cheetah.predator.core.SessionRegistry;
import cheetah.predator.protocol.protobuf.ProtocolConvertor;
import cheetah.predator.server.Bootstrap;
import cheetah.predator.spi.event.SessionListener;
import cheetah.predator.transport.SessionTransportConfig;
import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.List;

/**
 * Created by Max on 2016/3/13.
 */
public class PredatorStartup implements Bootstrap {
    private EventLoopGroup serverBossGroup;
    private EventLoopGroup serverWorkGroup;
    private SessionTransportConfig transportConfig;
    private final List<ChannelHandler> channelHandlers;
    private SessionRegistry sessionRegistry;
    private SessionListener sessionListener = event -> {};
    private List<Interceptor> interceptors;

    public PredatorStartup() {
        this.channelHandlers = Lists.newArrayList();
        this.interceptors = Lists.newArrayList();
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
    public void setHandlers(List<ChannelHandler> channelHandlers) {
        this.channelHandlers.clear();
        this.channelHandlers.addAll(channelHandlers);
    }

    @Override
    public void registerHandler(ChannelHandler channelHandler) {
        Assert.notNull(channelHandler);
        channelHandlers.add(channelHandler);
    }

    @Override
    public int getPort() {
        return transportConfig.port();
    }

    @Override
    public ChannelInitializer<Channel> generateChannelInitializer() {
        return new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                if (channelHandlers.isEmpty()) {
                    DispatcherSession dispatcher = new DispatcherSession();
                    dispatcher.setTransportConfig(transportConfig);
                    dispatcher.setSessionRegistry(sessionRegistry);
                    dispatcher.setInterceptors(interceptors);
                    dispatcher.setSessionListener(sessionListener);

                    channelHandlers.add(new SessionTrafficHandler(transportConfig.getTrafficLimit(), transportConfig.getTrafficCheckInterval()));
                    channelHandlers.add(new IdleStateHandler(0, 0, transportConfig.getIdleCheckPeriod()));
                    channelHandlers.add(new ProtobufVarint32FrameDecoder());
                    channelHandlers.add(new ProtobufDecoder(ProtocolConvertor.Protocol.getDefaultInstance()));
                    channelHandlers.add(new ProtobufVarint32LengthFieldPrepender());
                    channelHandlers.add(new ProtobufEncoder());
                    channelHandlers.add(dispatcher);
                    channelHandlers.add(new SessionIdleStateHandler(transportConfig.getIdleTimeout(), transportConfig.getIdleInitTimeout()));
                }
                channelHandlers.forEach(o -> channel.pipeline().addLast(o));
            }
        };
    }

    @Override
    public EventLoopGroup serverBossGroup() {
        return serverBossGroup;
    }

    @Override
    public EventLoopGroup serverWorkGroup() {
        return serverWorkGroup;
    }

    @Override
    public void setTransportConfig(SessionTransportConfig transportConfig) {
        this.transportConfig = transportConfig;
    }

    @Override
    public SessionTransportConfig transportConfig() {
        return transportConfig;
    }

    public PredatorStartup setSessionRegistry(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
        return this;
    }

    public PredatorStartup setSessionListener(SessionListener sessionListener) {
        this.sessionListener = sessionListener;
        return this;
    }

    public PredatorStartup setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
        return this;
    }
}
