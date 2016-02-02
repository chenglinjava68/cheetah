package cheetah.distributor;

import cheetah.event.Event;

/**
 * Created by Max on 2016/2/1.
*/
public interface Collector {
    void collect(Event event);
}
