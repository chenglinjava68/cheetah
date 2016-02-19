package cheetah.distributor;

import cheetah.event.Collector;
import cheetah.event.Event;

import java.util.EventListener;
import java.util.List;

/**
 * Created by Max on 2016/2/7.
 */
public interface Governor {
    EventResult allot(EventMessage eventMessage);

    void registrationCollector(Class<? extends Event> key, Collector collector);

    Collector arrangeCollector(Class<? extends Event> collectorType);

    void addListenerCache(Distributor.ListenerCacheKey cacheKey, List<EventListener> eventListeners);

    List<EventListener> getSmartEventListenerFromCache(Distributor.ListenerCacheKey cacheKey);

}
