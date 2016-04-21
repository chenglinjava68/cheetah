package cheetah.predator.core;

import cheetah.commons.net.Packet;
import com.google.common.base.MoreObjects;

/**
 * Created by Max on 2016/4/18.
 */
public class PreyPacket implements Packet {

    private static final long serialVersionUID = -3612760214098827317L;

    public final static int DEFAULT_CRC_CODE = 0xAEF0100;

    public final static byte CONNECT_TYPE = 1;
    public final static byte CONNECT_ACK_TYPE = 2;
    public final static byte CONNECT_RST_TYPE = 3;
    public final static byte PING_TYPE = 4;
    public final static byte PONG_TYPE = 5;
    public final static byte POST_TYPE = 6;
    public final static byte DISCONNECT_TYPE = 15;
    public final static int MIN_BODY_SIZE = 0;
    public final static int MAX_BODY_SIZE = 65536;
    public final static int MIN_DIGEST_SIZE = 0;
    public final static int MAX_DIGEST_SIZE = 256;
    public final static byte[] EMPTY = new byte[0];

    PreyPacket() {
        this.digest = EMPTY;
        this.body = EMPTY;
    }

    PreyPacket(int type, int crcCode, int digestSize, byte[] digest, int bodySize, byte[] body) {
        this.digest = EMPTY;
        this.body = EMPTY;
        this.crcCode = crcCode;
        this.type = type;
        this.digestSize = digestSize;
        this.digest = digest;
        this.bodySize = bodySize;
        this.body = body;
    }

    private int crcCode;
    private int type;
    private int digestSize;
    private byte[] digest;
    private int bodySize;
    private byte[] body;

    public int crcCode() {
        return this.crcCode;
    }

    public int type() {
        return type;
    }

    public int digestSize() {
        return this.digestSize;
    }

    public byte[] digest() {
        return this.digest;
    }

    public int bodySize() {
        return this.bodySize;
    }

    public byte[] body() {
        return this.body;
    }

    public PreyPacket crcCode(int crcCode) {
        return new PreyPacket(type, crcCode, this.digestSize, this.digest, this.bodySize, this.body);
    }

    public PreyPacket type(int type) {
        return new PreyPacket(type, crcCode, digestSize, digest, bodySize, body);
    }

    public PreyPacket digestSize(int digestSize) {
        return new PreyPacket(type, this.crcCode, digestSize, this.digest, this.bodySize, this.body);
    }

    public PreyPacket digest(byte[] digest) {
        return new PreyPacket(type, this.crcCode, digest.length, digest, this.bodySize, this.body);
    }

    public PreyPacket bodySize(int bodySize) {
        return new PreyPacket(type, this.crcCode, this.digestSize, this.digest, bodySize, this.body);
    }

    public PreyPacket body(byte[] body) {
        return new PreyPacket(type, this.crcCode, this.digestSize, this.digest, body.length, body);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("type", this.type).add("crcCode", this.crcCode).add("digestSize", this.digestSize).add("bodySize", this.bodySize).toString();
    }

    public static PreyPacket empty() {
        return new PreyPacket();
    }
}
