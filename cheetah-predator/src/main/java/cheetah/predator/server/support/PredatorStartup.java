package cheetah.predator.server.support;

import cheetah.commons.utils.CollectionUtils;
import cheetah.fighter.core.Interceptor;
import cheetah.predator.core.SessionRegistry;
import cheetah.predator.protocol.ProtocolConvertor;
import cheetah.predator.server.Bootstrap;
import cheetah.predator.spi.event.SessionListener;
import cheetah.predator.transport.SessionTransportConfig;
import com.google.common.collect.ImmutableList;
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
    private List<ChannelHandler> channelHandlers;
    private SessionRegistry sessionRegistry;
    private SessionListener sessionListener = event -> {
    };
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
    public void setChannelHandlers(List<ChannelHandler> channelHandlers) {
        this.channelHandlers = ImmutableList.<ChannelHandler>builder().addAll(channelHandlers).build();
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
                if (CollectionUtils.isEmpty(channelHandlers)) {
                    channelHandlers = ImmutableList.<ChannelHandler>builder()
                            .add(new SessionTrafficHandler(transportConfig.getTrafficLimit(), transportConfig.getTrafficCheckInterval()))
                            .add(new IdleStateHandler(0, 0, transportConfig.getIdleCheckPeriod()))
                            .add(new ProtobufVarint32FrameDecoder())
                            .add(new ProtobufDecoder(ProtocolConvertor.Message.getDefaultInstance()))
                            .add(new ProtobufVarint32LengthFieldPrepender())
                            .add(new ProtobufEncoder())
                            .add(new DispatcherSession(interceptors))
                            .add(new SessionIdleStateHandler(transportConfig.getIdleTimeout(), transportConfig.getIdleInitTimeout()))
                            .add(new SessionHandler(transportConfig, sessionRegistry, sessionListener))
                            .build();
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
        this.interceptors = ImmutableList.<Interceptor>builder().addAll(interceptors).build();
        return this;
    }
}
