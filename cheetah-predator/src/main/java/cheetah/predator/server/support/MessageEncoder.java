package cheetah.predator.server.support;

import cheetah.predator.core.PreyPacket;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by Max on 2016/4/21.
 */
public class MessageEncoder extends MessageToMessageDecoder<PreyPacket> {
    private ObjectMapper objectMapper;

    public MessageEncoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, PreyPacket preyPacket, List<Object> list) throws Exception {

    }
}
