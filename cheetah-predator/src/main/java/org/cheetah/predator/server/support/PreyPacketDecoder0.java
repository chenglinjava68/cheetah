package org.cheetah.predator.server.support;

import cheetah.commons.logger.Loggers;
import org.cheetah.predator.core.PreyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
 * Created by Max on 2016/4/22.
 */
public class PreyPacketDecoder0 extends LengthFieldBasedFrameDecoder {

    public PreyPacketDecoder0(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    public PreyPacketDecoder0(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    public PreyPacketDecoder0(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    public PreyPacketDecoder0(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        PreyPacket packet = PreyPacket.empty();
        int type = decodeFirstByte(in);
        short digestSize = decodeDigestSize(in);
        byte[] digest = decodeDigest(digestSize, in);
        int bodySize = decodeBodySize(in);
        byte[] body = decodeBody(bodySize, in);

        packet.type(type)
                .digestSize(digestSize)
                .digest(digest)
                .bodySize(bodySize)
                .body(body);

        Loggers.me().info(getClass(), "{} decode success.", packet);

        return packet;
    }

    private int decodeFirstByte(ByteBuf buf) {
        int type = buf.readInt();
        Loggers.me().info(getClass(), "decode first byte.");
//        int type = (firstByte >> 4) & 0x0F;// [0000]0000
//        int qos = firstByte & 0x0F;//0000[0000]
        return type;
    }

    private short decodeDigestSize(ByteBuf buf) {

        short digestSize = buf.readUnsignedByte();

        Loggers.me().info(getClass(), "decode digest size.");
        if (isUnexpectDigest(digestSize)) {
            throw new PacketException("", "digestSize over limit or invalid.", digestSize);
        }
        return digestSize;
    }

    private boolean isUnexpectDigest(short digestSize) {
        return digestSize > PreyPacket.MAX_DIGEST_SIZE || digestSize < PreyPacket.MIN_DIGEST_SIZE;
    }

    private byte[] decodeDigest(short digestSize, ByteBuf buf) {
        ByteBuf digest = buf.readBytes(digestSize);
        Loggers.me().debug(getClass(), "decode digest, expect {} bytes.", digestSize);
        return digest.array();
    }

    private int decodeBodySize(ByteBuf buf) {

        Loggers.me().info(getClass(), "decode body size.");
        int bodySize = buf.readUnsignedShort();
        if (isUnexpectBody(bodySize)) {
            throw new PacketException("", "bodySize over limit or invalid.", bodySize);
        }
        return bodySize;
    }

    private boolean isUnexpectBody(int bodySize) {
        return bodySize > PreyPacket.MAX_BODY_SIZE || bodySize < PreyPacket.MIN_BODY_SIZE;
    }

    private byte[] decodeBody(int bodySize, ByteBuf buf) {
        Loggers.me().info(getClass(), "decode body, expect {} bytes.", bodySize);

        ByteBuf body = buf.readBytes(bodySize);

        return body.array();

    }

}
