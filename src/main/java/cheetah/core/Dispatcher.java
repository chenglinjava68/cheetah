package cheetah.core;

import cheetah.machine.Machine;

import java.util.EventListener;
import java.util.Map;

/**
 * 调度接口
* Created by Max on 2016/2/23.
        */
public interface Dispatcher {
    /**
     * 接收一个事件消息
     * @param eventMessage
     * @return
     */
    EventResult receive(final EventMessage eventMessage);

    EventResult dispatch(final EventMessage eventMessage, final Map<Class<? extends EventListener>, Machine> machines);
}
