package cheetah.fighter.core.mechanism;

import cheetah.core.Interceptor;
import cheetah.core.support.HandlerInterceptorChain;
import cheetah.fighter.core.Dispatcher;
import cheetah.fighter.protocol.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * Created by Max on 2016/3/13.
 */
public class DispatcherMessage extends SimpleChannelInboundHandler<Message> implements Dispatcher {
    private List<Interceptor> interceptors;

    @Override
    public void dispatch(Message message) {

    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        HandlerInterceptorChain chain = createInterceptorChain();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cheetah.common.logger.Error.log(this.getClass(), cause.getMessage());
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
