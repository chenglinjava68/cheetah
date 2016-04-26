package org.cheetah.predator.server.support;

import org.cheetah.predator.core.Interceptor;
import org.cheetah.predator.core.SessionRegistry;
import org.cheetah.predator.core.support.SessionRegistryImpl;
import org.cheetah.predator.core.support.SessionTransportConfig;
import org.cheetah.predator.server.Bootstrap;
import org.cheetah.predator.server.PipelineCrowd;
import org.cheetah.predator.spi.event.SessionListener;
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
    private List<Interceptor> interceptors;
    private PipelineCrowd pipelineCrowd;
    private SessionListener sessionListener = event -> {
    };

    public PredatorStartup() {
        this.interceptors = Lists.newArrayList();
        this.sessionRegistry = new SessionRegistryImpl();
    }

    @Override
    public void initialize() {
        int processors = Runtime.getRuntime().availableProcessors();
        serverBossGroup = new NioEventLoopGroup(processors);
        serverWorkGroup = new NioEventLoopGroup();
    }

    @Override
    public int getPort() {
        return transportConfig.port();
    }

    @Override
    public void setPipelineCrowd(PipelineCrowd pipelineCrowd) {
        this.pipelineCrowd = pipelineCrowd;
    }

    @Override
    public ChannelInitializer<Channel> ChannelCrowd() {
        if (Objects.isNull(pipelineCrowd)) {
            PipelineCrowd crowd = new DefaultPipelineCrowd();
            crowd.setInterceptors(this.interceptors);
            crowd.setSessionListener(this.sessionListener);
            crowd.setSessionRegistry(this.sessionRegistry);
            crowd.setTransportConfig(this.transportConfig);
            return crowd;
        }
        return this.pipelineCrowd;
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
