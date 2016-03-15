package cheetah.predator.server.mechanism;

import io.netty.handler.traffic.ChannelTrafficShapingHandler;

/**
 * @author siuming
*/
public class SessionTrafficHandler extends ChannelTrafficShapingHandler {

    public SessionTrafficHandler(long readLimit, long checkInterval) {
        super(0, readLimit, checkInterval);
    }
}
