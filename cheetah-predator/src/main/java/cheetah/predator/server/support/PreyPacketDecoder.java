package cheetah.predator.server.support;

import cheetah.commons.logger.Loggers;
import cheetah.predator.core.PreyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.AttributeKey;

import java.util.List;

/**
 * @author Max
 */
final class PreyPacketDecoder extends ReplayingDecoder<PreyPacketDecoder.State> {

    static final AttributeKey<PreyPacket> PROTOCOL_KEY = AttributeKey.valueOf("PROTOCOL_KEY");

    PreyPacketDecoder() {
        super(State.INIT);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        // unnecessary to break on switch.
        // when there is not enough data to decode, it will cause exception which arrange by ReplayingDecoder
        switch (state()) {
            case INIT:
                initProtocol(ctx);
                checkpoint(State.FIRST_BYTE);
            case FIRST_BYTE:
                decodeFirstByte(ctx, buf);
                checkpoint(State.DIGEST_SIZE);
            case DIGEST_SIZE:
                decodeDigestSize(ctx, buf);
                checkpoint(State.DIGEST);
            case DIGEST:
                decodeDigest(ctx, buf);
                checkpoint(State.BODY_SIZE);
            case BODY_SIZE:
                decodeBodySize(ctx, buf);
                checkpoint(State.BODY);
            case BODY:
                decodeBody(ctx, buf, out);
                checkpoint(State.INIT);
        }
    }

    private void initProtocol(ChannelHandlerContext ctx) {
        ctx.attr(PROTOCOL_KEY).set(PreyPacket.empty());
    }

    private void decodeFirstByte(ChannelHandlerContext ctx, ByteBuf buf) {
        byte type = buf.readByte();
        Loggers.me().info(getClass(), "decode first byte.");
//        int type = (firstByte >> 4) & 0x0F;// [0000]0000
//        int qos = firstByte & 0x0F;//0000[0000]
        PreyPacket packet = ctx.attr(PROTOCOL_KEY).get().type(type);
        ctx.attr(PROTOCOL_KEY).set(packet);
    }

    private void decodeDigestSize(ChannelHandlerContext ctx, ByteBuf buf) {

        PreyPacket packet = ctx.attr(PROTOCOL_KEY).get();
        short digestSize = buf.readUnsignedByte();

        Loggers.me().info(getClass(), "decode digest size.");
        if (isUnexpectDigest(digestSize)) {
            throw new PacketException("", "digestSize over limit or invalid.", digestSize);
        }

        packet = packet.digestSize(digestSize);
        ctx.attr(PROTOCOL_KEY).set(packet);
    }

    private boolean isUnexpectDigest(short digestSize) {
        return digestSize > PreyPacket.MAX_DIGEST_SIZE || digestSize < PreyPacket.MIN_DIGEST_SIZE;
    }

    private void decodeDigest(ChannelHandlerContext ctx, ByteBuf buf) {
        PreyPacket packet = ctx.attr(PROTOCOL_KEY).get();
        ByteBuf digest = buf.readBytes(packet.digestSize());
        Loggers.me().debug(getClass(), "decode digest, expect {} bytes.", packet.digestSize());

        packet = packet.digest(digest.array());
        ctx.attr(PROTOCOL_KEY).set(packet);
    }

    private void decodeBodySize(ChannelHandlerContext ctx, ByteBuf buf) {
        PreyPacket packet = ctx.attr(PROTOCOL_KEY).get();

        Loggers.me().info(getClass(), "decode body size.");
        int bodySize = buf.readUnsignedShort();
        if (isUnexpectBody(bodySize)) {
            throw new PacketException("", "bodySize over limit or invalid.", bodySize);
        }

        packet = packet.bodySize(bodySize);
        ctx.attr(PROTOCOL_KEY).set(packet);
    }

    private boolean isUnexpectBody(int bodySize) {
        return bodySize > PreyPacket.MAX_BODY_SIZE || bodySize < PreyPacket.MIN_BODY_SIZE;
    }

    private void decodeBody(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) {
        PreyPacket packet = ctx.attr(PROTOCOL_KEY).get();
        Loggers.me().info(getClass(), "decode body, expect {} bytes.", packet.bodySize());

        ByteBuf body = buf.readBytes(packet.bodySize());

        packet = packet.body(body.array());
        ctx.attr(PROTOCOL_KEY).set(packet);
        out.add(packet);

        Loggers.me().info(getClass(), "{} decode success.", packet);
    }

    /**
     * @author siuming
     */
    enum State {
        INIT, FIRST_BYTE, DIGEST_SIZE, DIGEST, BODY_SIZE, BODY
    }
}
