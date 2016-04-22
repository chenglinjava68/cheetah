package cheetah.predator.server.support;

import cheetah.commons.logger.Loggers;
import cheetah.predator.core.PreyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Max
 */
final class PreyPacketEncoder extends MessageToByteEncoder<PreyPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, PreyPacket packet, ByteBuf buf) throws Exception {

        if (isUnexpectDigest(packet)) {
            throw new PacketException("", "digestSize over limit or invalid.", packet.digestSize());
        }

        if (isUnexpectBody(packet)) {
            throw new PacketException("", "bodySize over limit or invalid.", packet.bodySize());
        }

        int crcCode = (packet.crcCode() << 8) | packet.type();
        buf.writeByte(crcCode);
        buf.writeByte(packet.digestSize());
        buf.writeBytes(packet.digest());
        buf.writeShort(packet.bodySize());
        buf.writeBytes(packet.body());
        Loggers.me().info(getClass(), "{} encode success.", packet);
    }

    private boolean isUnexpectDigest(PreyPacket packet) {
        return packet.digestSize() > PreyPacket.MAX_DIGEST_SIZE || packet.digestSize() < PreyPacket.MIN_DIGEST_SIZE;
    }

    private boolean isUnexpectBody(PreyPacket packet) {
        return packet.bodySize() > PreyPacket.MAX_BODY_SIZE || packet.bodySize() < PreyPacket.MIN_BODY_SIZE;
    }

    public static void main(String[] args) {
        System.out.println(0xAEFB0100 >> 16 & 0XFFFF);
        System.out.println(Integer.toBinaryString(0xF));
        System.out.println(Integer.toBinaryString(0xaf21));
        System.out.println(Integer.toBinaryString(0xaf21 & 0xff)); //版本号
        System.out.println(Integer.toBinaryString(0xaf21 >> 4 & 0xfff));  //校验码
    }
}
