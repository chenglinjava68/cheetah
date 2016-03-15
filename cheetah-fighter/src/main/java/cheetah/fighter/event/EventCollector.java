package cheetah.fighter.event;

import cheetah.fighter.core.EventResult;

/**
 * 事件收集器
 * Created by Max on 2016/2/1.
*/
public interface EventCollector {

    void collect(Event event);

    void collect(Event event, boolean fisrtWin);

    EventResult collect(boolean needResult, Event event);

    EventResult collect(boolean needResult, boolean fisrtWin, Event event);
}
