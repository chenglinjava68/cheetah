package cheetah.predator.server.support;

import io.netty.channel.ChannelHandlerAppender;

/**
 * @author Max
 */
public class PreyPacketCodec extends ChannelHandlerAppender {

    public PreyPacketCodec() {
        super(new PreyPacketDecoder(), new PreyPacketEncoder());
    }
}
