package cheetah.distributor.event;

import cheetah.distributor.core.EventResult;

/**
 * Created by Max on 2016/2/1.
*/
public interface Collector {
    int UNIMPEDED = 0;
    int STATE = 1;

    void collect(Event event);

    void collect(Event event, boolean fisrtWin);

    EventResult collect(boolean needResult, Event event);

    EventResult collect(boolean needResult, boolean fisrtWin, Event event);


}
