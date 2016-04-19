package cheetah.predator.core;

import cheetah.commons.net.Packet;
import com.google.common.base.MoreObjects;

/**
 * Created by pdemo on 2016/4/18.
 */
public class MyPacket implements Packet {

    private static final long serialVersionUID = -3612760214098827317L;

    MyPacket() {
        this.digest = EMPTY;
        this.body = EMPTY;
    }

    MyPacket(byte type, int digestSize, byte[] digest, int bodySize, byte[] body) {
        this.digest = EMPTY;
        this.body = EMPTY;
        this.type = type;
        this.digestSize = digestSize;
        this.digest = digest;
        this.bodySize = bodySize;
        this.body = body;
    }

    private byte type;
    private int digestSize;
    private byte[] digest;
    private int bodySize;
    private byte[] body;

    @Override
    public byte type() {
        return this.type;
    }

    @Override
    public int digestSize() {
        return this.digestSize;
    }

    @Override
    public byte[] digest() {
        return this.digest;
    }

    @Override
    public int bodySize() {
        return this.bodySize;
    }

    @Override
    public byte[] body() {
        return this.body;
    }

    @Override
    public Packet type(byte type) {
        return new MyPacket(type, this.digestSize, this.digest, this.bodySize, this.body);
    }

    @Override
    public Packet digestSize(int digestSize) {
        return new MyPacket(this.type, digestSize, this.digest, this.bodySize, this.body);
    }

    @Override
    public Packet digest(byte[] digest) {
        return new MyPacket(this.type, digest.length, digest, this.bodySize, this.body);
    }

    @Override
    public Packet bodySize(int bodySize) {
        return new MyPacket(this.type, this.digestSize, this.digest, bodySize, this.body);
    }

    @Override
    public Packet body(byte[] body) {
        return new MyPacket(this.type, this.digestSize, this.digest, body.length, body);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("type", this.type).add("digestSize", this.digestSize).add("bodySize", this.bodySize).toString();
    }

    public static MyPacket empty() {
        return new MyPacket();
    }
}
