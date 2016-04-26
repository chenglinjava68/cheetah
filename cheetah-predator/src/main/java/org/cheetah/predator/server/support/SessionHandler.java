package org.cheetah.predator.server.support;

import org.cheetah.commons.logger.Loggers;
import org.cheetah.predator.core.Session;
import org.cheetah.predator.core.SessionRegistry;
import org.cheetah.predator.core.support.SessionHolder;
import org.cheetah.predator.core.support.SessionImpl;
import org.cheetah.predator.core.support.SessionTransportConfig;
import org.cheetah.predator.spi.event.SessionEvent;
import org.cheetah.predator.spi.event.SessionListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;

/**
 * Created by Max on 2016/3/26.
 */
public class SessionHandler extends ChannelInboundHandlerAdapter {
    private SessionRegistry sessionRegistry;
    private SessionTransportConfig transportConfig;
    private SessionListener sessionListener = event -> {
    };

    public SessionHandler(SessionTransportConfig transportConfig, SessionRegistry sessionRegistry, SessionListener sessionListener) {
        this.transportConfig = transportConfig;
        this.sessionListener = sessionListener;
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Session.Metadata metadata = Session.Metadata.newBuilder()
                .state(Session.State.UNAPPROVED)
                .accessEndpoint(transportConfig.accessEndpoint().toString())
                .remoteEndpoint(ofRemoteEndpoint(ctx))
                .build();

        Session session = new SessionImpl(ctx);
        session.metadata(metadata);

        SessionHolder.setSession(ctx, session);
        sessionRegistry.register(session);
        userEventTriggered(ctx, anOpenedEventWith(session));
    }

    private SessionEvent anOpenedEventWith(Session session) {
        return SessionEvent.of(SessionEvent.Type.OPENED, session);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (!(evt instanceof SessionEvent)) {
            ctx.fireUserEventTriggered(evt);
            return;
        }

        SessionEvent anEvent = (SessionEvent) evt;
        fire(sessionListener, anEvent);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Session session = SessionHolder.getAndRemoveSession(ctx);
        userEventTriggered(ctx, aClosedEventWith(session));
        sessionRegistry.unregister(session);
        Loggers.me().debug(this.getClass(), "[UNREGISTRY]" + session.metadata().toString());
    }

    private SessionEvent aClosedEventWith(Session session) {
        return SessionEvent.of(SessionEvent.Type.CLOSED, session);
    }

    private void fire(SessionListener sessionListener, SessionEvent event) {
        sessionListener.onEvent(event);
    }

    private String ofRemoteEndpoint(ChannelHandlerContext ctx) {
        // Important. using remoteAddress.getHostName may cause a dns lookup.
        // use remoteAddress.getHostString instead.
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        return String.format("%s://%s:%s", transportConfig.scheme(), remoteAddress.getHostString(), remoteAddress.getPort());
    }

    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    public void setTransportConfig(SessionTransportConfig sessionConfig) {
        this.transportConfig = sessionConfig;
    }

    public void setSessionListener(SessionListener sessionListener) {
        this.sessionListener = sessionListener;
    }
}
