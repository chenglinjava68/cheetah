package cheetah.predator.server.support;

import cheetah.commons.logger.Info;
import cheetah.commons.logger.Loggers;
import cheetah.predator.core.PreyPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Max
 */
public final class PreyPacketHandler extends SimpleChannelInboundHandler<PreyPacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Loggers.me().error(getClass(), "handle packet occurs error.", cause);
        super.exceptionCaught(ctx, cause);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PreyPacket packet) throws Exception {
        Info.log(this.getClass(), "data received: " + packet);
    }

}
