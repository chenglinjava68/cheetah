package cheetah.predator.transport;

import cheetah.predator.core.Session;
import cheetah.predator.protocol.Message;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Max on 2016/3/13.
 */
public class SessionImpl implements Session {
    private final ChannelHandlerContext ctx;

    public SessionImpl(ChannelHandlerContext channelHandlerContext) {
        this.ctx = channelHandlerContext;
    }

    @Override
    public void respond(Message message) {
        this.ctx.writeAndFlush(message);
    }

    @Override
    public void close(Message message) throws Exception {
        Object out = null == message ? new byte[0] : message;
        this.ctx.writeAndFlush(out).addListener(ChannelFutureListener.CLOSE);
    }
}
