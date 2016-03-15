package cheetah.fighter.fighter.server;

import cheetah.fighter.commons.Startable;
import io.netty.channel.ChannelHandler;

import java.util.List;

/**
 * Created by Max on 2016/3/13.
 */
public interface Bootstrap extends Startable {

    void initialize();

    void registerHandler(List<ChannelHandler> channelHandlers);

    void registerHandler(ChannelHandler channelHandler);

    void setPort(int port);


}
