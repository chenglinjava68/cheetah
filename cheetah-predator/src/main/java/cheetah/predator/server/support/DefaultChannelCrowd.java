package cheetah.predator.server.support;

import cheetah.predator.protocol.ProtocolConvertor;
import cheetah.predator.server.ChannelCrowd;
import io.netty.channel.Channel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by Max on 2016/3/26.
 */
public class DefaultChannelCrowd extends ChannelCrowd {

    @Override
    protected void doInitialize(Channel channel) {
        channel.pipeline().addLast(new SessionTrafficHandler(transportConfig().getTrafficLimit(), transportConfig().getTrafficCheckInterval()));
        channel.pipeline().addLast(new IdleStateHandler(0, 0, transportConfig().getIdleCheckPeriod()));
        channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
        channel.pipeline().addLast(new ProtobufDecoder(ProtocolConvertor.Message.getDefaultInstance()));
        channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
        channel.pipeline().addLast(new ProtobufEncoder());
        channel.pipeline().addLast(new DispatcherMessage(interceptors()));
        channel.pipeline().addLast(new SessionIdleStateHandler(transportConfig().getIdleTimeout(), transportConfig().getIdleInitTimeout()));
        channel.pipeline().addLast(new SessionHandler(transportConfig(), sessionRegistry(), sessionListener()));
    }

}
