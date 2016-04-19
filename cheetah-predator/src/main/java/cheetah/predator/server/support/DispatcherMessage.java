package cheetah.predator.server.support;

import cheetah.commons.logger.Debug;
import cheetah.commons.logger.Loggers;
import cheetah.predator.core.Interceptor;
import cheetah.predator.core.Session;
import cheetah.predator.core.support.MessageHandlerChain;
import cheetah.predator.core.support.SessionHolder;
import cheetah.predator.protocol.MessageBuf;
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
public final class DispatcherMessage extends SimpleChannelInboundHandler<MessageBuf.Message> {
    private List<Interceptor> interceptors;

    public DispatcherMessage(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, MessageBuf.Message message) throws Exception {
        MessageHandlerChain chain = createInterceptorChain(message.getHeader().getType());
        Session session = SessionHolder.getSession(ctx);
        chain.handle(message, session);
        Debug.log(this.getClass(), message.toString());
        ctx.writeAndFlush(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof DecoderException
                || cause instanceof EncoderException
                || cause instanceof MessageInterceptorNotFoundException) {

            Loggers.me().warn(getClass(), "packet or message codec exception or handler not found exception, for prevent dirty data, close session.", cause);

            Session session =  SessionHolder.getSession(ctx);
            MessageBuf.Message reason = buildMessage(session);

            ctx.writeAndFlush(reason).addListener(ChannelFutureListener.CLOSE);
            return;
        }

        // unknown error, close session.
        Loggers.me().error(getClass(), "handle message occurs error. close session.", cause);
        ctx.close();
    }

    private MessageBuf.Message buildMessage(Session session) {
        return null;
    }

    MessageHandlerChain createInterceptorChain(int messageType) {
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

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

}
