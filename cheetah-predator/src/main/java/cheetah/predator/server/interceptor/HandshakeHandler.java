package cheetah.predator.server.interceptor;

import cheetah.predator.core.Interceptor;
import cheetah.predator.core.Message;
import cheetah.predator.core.MessageType;
import cheetah.predator.core.Session;

/**
 * Created by Max on 2016/3/29.
 */
public class HandshakeHandler implements Interceptor {


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

    @Override
    public boolean handle(Message message, Session session) throws Exception {
        return false;
    }

    @Override
    public void afterCompletion(Message message, Session session, Exception ex) throws Exception {

    }

    @Override
    public boolean supportsType(MessageType type) {
        return false;
    }
}
