package org.cheetah.predator.server.support;

import cheetah.commons.PlatformException;

/**
 * Created by Max on 2016/4/19.
 */
public class PacketException extends PlatformException {
    public PacketException() {
        super("");
    }

    public PacketException(String errorCode, String message, Object... params) {
        super(errorCode, new Object[]{message, params});
    }
}
