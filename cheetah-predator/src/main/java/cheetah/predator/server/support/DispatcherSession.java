package cheetah.predator.server.support;

import cheetah.commons.logger.Debug;
import cheetah.fighter.core.Interceptor;
import cheetah.fighter.core.support.HandlerInterceptorChain;
import cheetah.predator.core.Dispatcher;
import cheetah.predator.core.Session;
import cheetah.predator.protocol.ProtocolConvertor;
import cheetah.predator.transport.SessionHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * Created by Max on 2016/3/13.
 */
public class DispatcherSession extends SimpleChannelInboundHandler<ProtocolConvertor.Message> implements Dispatcher {
    private List<Interceptor> interceptors;

    public DispatcherSession(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    public void dispatch(ProtocolConvertor.Message message) {

    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ProtocolConvertor.Message message) throws Exception {
        HandlerInterceptorChain chain = createInterceptorChain();
        Session session = SessionHolder.getSession(ctx);
        System.out.println(message.getSerializedSize());
        System.out.println(message.getHeader().getDeliveryId());
        Debug.log(this.getClass(), message.toString());
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

}
