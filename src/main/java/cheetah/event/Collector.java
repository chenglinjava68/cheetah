package cheetah.event;

import cheetah.event.Event;

/**
 * Created by Max on 2016/2/1.
 */
public interface Collector {
    int UNIMPEDED = 0;
    int NATIVE = 1;
    int STATE = 2;
    int STATE_CALL_BACK = 3;

    void collect(Event event);

    void collect(Event event, int mode);

    void collect(Event event, int mode, boolean fisrtWin);

}
