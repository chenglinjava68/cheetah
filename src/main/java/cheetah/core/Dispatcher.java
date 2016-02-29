package cheetah.core;

import cheetah.machine.Machine;

import java.util.EventListener;
import java.util.Map;

/**
 * Created by Max on 2016/2/23.
 */
public interface Dispatcher {
    EventResult receive(EventMessage eventMessage);

    EventResult dispatch(EventMessage eventMessage, Map<Class<? extends EventListener>, Machine> machines);
}
