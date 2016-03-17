package cheetah.predator.server.support;

import cheetah.commons.logger.Loggers;
import cheetah.fighter.core.Interceptor;
import cheetah.fighter.core.support.HandlerInterceptorChain;
import cheetah.predator.core.Dispatcher;
import cheetah.predator.core.Session;
import cheetah.predator.core.SessionRegistry;
import cheetah.predator.protocol.Message;
import cheetah.predator.spi.event.SessionEvent;
import cheetah.predator.spi.event.SessionListener;
import cheetah.predator.transport.SessionHolder;
import cheetah.predator.transport.SessionTransportConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by Max on 2016/3/13.
 */
public class DispatcherSession extends SimpleChannelInboundHandler<Message> implements Dispatcher {
    private List<Interceptor> interceptors;
    private SessionRegistry sessionRegistry;
    private SessionTransportConfig sessionConfig;
    private SessionListener sessionListener;

    @Override
    public void dispatch(Message message) {

    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Message message) throws Exception {
        HandlerInterceptorChain chain = createInterceptorChain();
        Session session = SessionHolder.getSession(ctx);
//        Optional<io.workplus.foundations.sessioncenter.spi.MessageHandler> handlerOpt = getHandler(msg);
//        if (!handlerOpt.isPresent()) {
//            throw new MessageHandlerNotFoundException();
//        }
//
//        SessionContext context = SessionContext.from(session);
//        handlerOpt.get().handle(context, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cheetah.commons.logger.Error.log(this.getClass(), cause.getMessage());
        ctx.close();
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
        Loggers.me().debug(this.getClass(), session.metadata().toString() + "-- close.");
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
        return String.format("%s://%s:%s", sessionConfig.scheme(), remoteAddress.getHostString(), remoteAddress.getPort());
    }


    HandlerInterceptorChain createInterceptorChain() {
        HandlerInterceptorChain chain;
        try {
            chain = HandlerInterceptorChain.createChain();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            chain = new HandlerInterceptorChain();
        }
        interceptors().forEach(chain::register);
        return chain;
    }

    public List<Interceptor> interceptors() {
        return interceptors;
    }

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    public void setSessionConfig(SessionTransportConfig sessionConfig) {
        this.sessionConfig = sessionConfig;
    }

    public void setSessionListener(SessionListener sessionListener) {
        this.sessionListener = sessionListener;
    }
}
