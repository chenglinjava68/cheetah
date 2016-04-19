package cheetah.commons.net;

import java.io.Serializable;

/**
 * Created by pdemo on 2016/4/18.
 */
public interface Packet extends Serializable {
    byte CONNECT_TYPE = 1;
    byte CONNECT_ACK_TYPE = 2;
    byte CONNECT_RST_TYPE = 3;
    byte PING_TYPE = 4;
    byte PONG_TYPE = 5;
    byte POST_TYPE = 6;
    byte DISCONNECT_TYPE = 15;
    int MIN_BODY_SIZE = 0;
    int MAX_BODY_SIZE = 65536;
    int MIN_DIGEST_SIZE = 0;
    int MAX_DIGEST_SIZE = 256;
    byte[] EMPTY = new byte[0];

    byte type();

    int digestSize();

    byte[] digest();

    int bodySize();

    byte[] body();

    Packet type(byte type);

    Packet digestSize(int digestSize);

    Packet digest(byte[] digest);

    Packet bodySize(int bodySize);

    Packet body(byte[] body);

}
