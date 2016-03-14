package cheetah.fighter.transport;

import cheetah.fighter.core.Session;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Max on 2016/3/13.
 */
public class SessionImpl implements Session<ChannelHandlerContext> {
    private final ChannelHandlerContext channelHandlerContext;

    public SessionImpl(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public ChannelHandlerContext getSession() {
        return channelHandlerContext;
    }
}
