package cheetah.distributor;

import cheetah.distributor.handler.EventMessage;
import cheetah.distributor.handler.EventResult;

import java.util.EventListener;
import java.util.List;

/**
 * Created by Max on 2016/2/19.
 */
public interface Engine {
    EventResult handle(EventMessage eventMessage, List<EventListener> eventListeners);
}
