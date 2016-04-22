package cheetah.predator.server.support;

import cheetah.predator.server.PipelineCrowd;
import io.netty.channel.Channel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by Max on 2016/3/26.
 */
public class DefaultPipelineCrowd extends PipelineCrowd {

    @Override
    protected void doInitialize(Channel channel) {
        channel.pipeline().addLast(new SessionTrafficHandler(transportConfig().getTrafficLimit(), transportConfig().getTrafficCheckInterval()));
        channel.pipeline().addLast(new IdleStateHandler(0, 0, transportConfig().getIdleCheckPeriod()));
        channel.pipeline().addLast(new SessionHandler(transportConfig(), sessionRegistry(), sessionListener()));
        channel.pipeline().addLast(new PreyPacketCodec());
        channel.pipeline().addLast(new MessageCodec());
        channel.pipeline().addLast(new PreyPacketHandler());
        channel.pipeline().addLast(new DispatcherMessage(interceptors()));
        channel.pipeline().addLast(new SessionIdleStateHandler(transportConfig().getIdleTimeout(), transportConfig().getIdleInitTimeout()));
    }

}
