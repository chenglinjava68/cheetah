package cheetah.predator.server.support;

import cheetah.commons.logger.Loggers;
import cheetah.predator.core.Message;
import cheetah.predator.core.PreyPacket;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/4/20.
 */
public class MessageDecoder extends MessageToMessageDecoder<PreyPacket> {
    private ObjectMapper objectMapper;

    public MessageDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, PreyPacket packet, List<Object> list) throws Exception {
        // convert body as Message directly will lost Message.body type info.
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        JavaType type = typeFactory.constructMapType(HashMap.class, String.class, Object.class);
        Map<String, Object> content = objectMapper.readValue(packet.body(), type);

        Message message = objectMapper.convertValue(content, Message.class);
        list.add(message);
        Loggers.me().info(
                getClass(),
                "decode message success. from:{}, to:{}, type:{}, digestSize:{}, bodySize:{}.",
                message.from(), message.to(), message.type(), packet.digestSize(), packet.bodySize()
        );
    }
}
