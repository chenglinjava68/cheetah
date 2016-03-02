package cheetah.event;

import cheetah.client.ProcessType;
import cheetah.core.EventResult;

/**
 * 事件收集器
 * Created by Max on 2016/2/1.
*/
public interface Collector {

    void collect(Event event);
    void collect(Event event, ProcessType processType);

    void collect(Event event, boolean fisrtWin);
    void collect(Event event, boolean fisrtWin, ProcessType processType);

    EventResult collect(boolean needResult, Event event);
    EventResult collect(boolean needResult, Event event, ProcessType processType);

    EventResult collect(boolean needResult, boolean fisrtWin, Event event);
    EventResult collect(boolean needResult, boolean fisrtWin, Event event, ProcessType processType);
}
