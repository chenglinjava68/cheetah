package cheetah.predator.server.support;

import cheetah.commons.logger.Loggers;
import cheetah.commons.net.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Max
 */
final class PreyPacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf buf) throws Exception {

        if (isUnexpectDigest(packet)) {
            throw new PacketException("", "digestSize over limit or invalid.", packet.digestSize());
        }

        if (isUnexpectBody(packet)) {
            throw new PacketException("", "bodySize over limit or invalid.", packet.bodySize());
        }

//        int firstByte = (packet.type() << 4) | packet.qos();
        buf.writeByte(packet.type());
        buf.writeByte(packet.digestSize());
        buf.writeBytes(packet.digest());
        buf.writeShort(packet.bodySize());
        buf.writeBytes(packet.body());
        Loggers.me().info(getClass(), "{} encode success.", packet);
    }

    private boolean isUnexpectDigest(Packet packet) {
        return packet.digestSize() > Packet.MAX_DIGEST_SIZE || packet.digestSize() < Packet.MIN_DIGEST_SIZE;
    }

    private boolean isUnexpectBody(Packet packet) {
        return packet.bodySize() > Packet.MAX_BODY_SIZE || packet.bodySize() < Packet.MIN_BODY_SIZE;
    }
}
