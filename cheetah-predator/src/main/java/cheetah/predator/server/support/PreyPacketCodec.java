package cheetah.predator.server.support;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * @author Max
 */
public class PreyPacketCodec extends CombinedChannelDuplexHandler {

    public PreyPacketCodec() {
        super(new PreyPacketDecoder0(1024, 0, 4, -4, 0), new PreyPacketEncoder());
    }
}
