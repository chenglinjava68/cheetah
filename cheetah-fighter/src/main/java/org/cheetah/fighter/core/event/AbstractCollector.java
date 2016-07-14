package org.cheetah.fighter.core.event;


import org.cheetah.fighter.core.EventBus;

/**
 * 事件收集器的抽象类
 * Created by Max on 2016/2/3.
 */
public abstract class AbstractCollector implements EventCollector {
    private EventBus eventBus;

    public AbstractCollector() {
    }

    public AbstractCollector(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }
}
