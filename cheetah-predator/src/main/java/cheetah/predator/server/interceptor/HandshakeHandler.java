package cheetah.predator.server.interceptor;

import cheetah.predator.core.Interceptor;
import cheetah.predator.core.Session;
import cheetah.predator.protocol.MessageBuf;

/**
 * Created by Max on 2016/3/29.
 */
public class HandshakeHandler implements Interceptor {

    @Override
    public boolean handle(MessageBuf.Message message, Session session) throws Exception {
        return false;
    }

    @Override
    public void afterCompletion(MessageBuf.Message message, Session session, Exception ex) throws Exception {

    }

    @Override
    public boolean supportsType(int messageType) {
        return false;
    }

    public static void main(String[] args) {
        short s = 10;
        System.out.println((s >> 4) & 0x0f);
    }

    public static byte[] shortToByteArray(short s) {
        byte[] targets = new byte[2];
        for (int i = 0; i < 2; i++) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((s >>> offset) & 0xff);
        }
        return targets;
    }

}
