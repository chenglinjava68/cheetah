package org.cheetah.predator.server.support;

import org.cheetah.commons.logger.Debug;
import org.cheetah.commons.logger.Loggers;
import org.cheetah.predator.core.*;
import org.cheetah.predator.core.support.MessageHandlerChain;
import org.cheetah.predator.core.support.SessionHolder;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Max on 2016/3/13.
 */
public final class DispatcherMessage extends SimpleChannelInboundHandler<Message> {
    private List<Interceptor> interceptors;
    private List<MessageHandler> messageHandlers;
    private SessionRegistry sessionRegistry;

    public DispatcherMessage(List<Interceptor> interceptors, List<MessageHandler> messageHandlers, SessionRegistry sessionRegistry) {
        this.interceptors = interceptors;
        this.messageHandlers = messageHandlers;
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        MessageHandlerChain chain = createInterceptorChain(message.getType());
        Session session = SessionHolder.getSession(ctx);
        chain.handle(message, session);
        Debug.log(this.getClass(), message.toString());
        System.out.println(sessionRegistry.size());
        ctx.writeAndFlush(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof DecoderException
                || cause instanceof EncoderException
                || cause instanceof MessageInterceptorNotFoundException) {

            Loggers.me().warn(getClass(), "packet or message codec exception or handler not found exception, for prevent dirty data, close session.", cause);

            Session session =  SessionHolder.getSession(ctx);
            Message reason = buildMessage(session);

            ctx.writeAndFlush(reason).addListener(ChannelFutureListener.CLOSE);
            return;
        }

        // unknown error, close session.
        Loggers.me().error(getClass(), "handle message occurs error. close session.", cause);
        ctx.close();
    }

    private Message buildMessage(Session session) {
        return null;
    }

    MessageHandlerChain createInterceptorChain(MessageType messageType) {
        MessageHandlerChain chain;
        try {
            chain = MessageHandlerChain.of();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            chain = new MessageHandlerChain();
        }
        List<Interceptor> list = interceptors().stream().filter(o -> o.supportsType(messageType)).collect(Collectors.toList());
        if (list.isEmpty())
            throw new MessageInterceptorNotFoundException();
        chain.register(list);
        return chain;
    }

    public List<Interceptor> interceptors() {
        return interceptors;
    }

    public List<MessageHandler> messageHandlers() {
        return messageHandlers;
    }

    public SessionRegistry sessionRegistry() {
        return sessionRegistry;
    }
}
