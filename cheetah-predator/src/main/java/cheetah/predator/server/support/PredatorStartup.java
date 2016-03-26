package cheetah.predator.server.support;

import cheetah.fighter.core.Interceptor;
import cheetah.predator.core.SessionRegistry;
import cheetah.predator.server.Bootstrap;
import cheetah.predator.server.ChannelCrowd;
import cheetah.predator.spi.event.SessionListener;
import cheetah.predator.core.support.SessionTransportConfig;
import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.List;
import java.util.Objects;

/**
 * Created by Max on 2016/3/13.
 */
public class PredatorStartup implements Bootstrap {
    private EventLoopGroup serverBossGroup;
    private EventLoopGroup serverWorkGroup;
    private SessionTransportConfig transportConfig;
    private SessionRegistry sessionRegistry;
    private SessionListener sessionListener = event -> {
    };
    private List<Interceptor> interceptors;
    private ChannelCrowd channelCrowd;

    public PredatorStartup() {
        this.interceptors = Lists.newArrayList();
    }

    @Override
    public void initialize() {
        int processors = Runtime.getRuntime().availableProcessors();
        serverBossGroup = new NioEventLoopGroup(processors);
        serverWorkGroup = new NioEventLoopGroup((int) (processors / (1 - 0.6)));
    }

    @Override
    public int getPort() {
        return transportConfig.port();
    }

    @Override
    public void setChannelCrowd(ChannelCrowd channelCrowd) {
        this.channelCrowd = channelCrowd;
    }

    @Override
    public ChannelInitializer<Channel> ChannelCrowd() {
        if (Objects.isNull(channelCrowd)) {
            ChannelCrowd crowd = new DefaultChannelCrowd();
            crowd.setInterceptors(this.interceptors);
            crowd.setSessionListener(this.sessionListener);
            crowd.setSessionRegistry(this.sessionRegistry);
            crowd.setTransportConfig(this.transportConfig);
            return crowd;
        }
        return this.channelCrowd;
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

}
