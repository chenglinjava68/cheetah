package org.cheetah.predator.server.support;

import io.netty.handler.traffic.ChannelTrafficShapingHandler;

/**
 * @author Max
*/
public class SessionTrafficHandler extends ChannelTrafficShapingHandler {

    public SessionTrafficHandler(long readLimit, long checkInterval) {
        super(0, readLimit, checkInterval);
    }
}
