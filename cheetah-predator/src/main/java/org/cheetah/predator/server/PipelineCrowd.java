package org.cheetah.predator.server;

import org.cheetah.predator.core.Interceptor;
import org.cheetah.predator.core.SessionRegistry;
import org.cheetah.predator.core.support.SessionTransportConfig;
import org.cheetah.predator.spi.event.SessionListener;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

import java.util.List;


/**
 * Created by Max on 2016/3/26.
 */
public abstract class PipelineCrowd extends ChannelInitializer<Channel> {
    private SessionTransportConfig transportConfig;
    private SessionRegistry sessionRegistry;
    private SessionListener sessionListener;
    private List<Interceptor> interceptors;

    @Override
    protected void initChannel(Channel channel) throws Exception {
        doInitialize(channel);
    }

    protected abstract void doInitialize(Channel channel);

    public SessionTransportConfig transportConfig() {
        return transportConfig;
    }

    public PipelineCrowd setTransportConfig(SessionTransportConfig transportConfig) {
        this.transportConfig = transportConfig;
        return this;
    }

    public SessionRegistry sessionRegistry() {
        return sessionRegistry;
    }

    public PipelineCrowd setSessionRegistry(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
        return this;
    }

    public SessionListener sessionListener() {
        return sessionListener;
    }

    public PipelineCrowd setSessionListener(SessionListener sessionListener) {
        this.sessionListener = sessionListener;
        return this;
    }

    public List<Interceptor> interceptors() {
        return interceptors;
    }

    public PipelineCrowd setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
        return this;
    }
}
