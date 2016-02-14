package cheetah.distributor.handler;

import cheetah.event.Event;

/**
 * 事件处理器
 * Created by Max on 2016/2/1.
 */
public interface Handler {
    void handle(Event event);
}
