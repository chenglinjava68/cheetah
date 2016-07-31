package org.cheetah.fighter;

import java.util.concurrent.TimeUnit;

/**
 * 事件收集器
 * Created by Max on 2016/2/1.
*/
public interface EventCollector {

    void collect(DomainEvent event);

    void collect(DomainEvent event, int timeout);

    EventResult collect(DomainEvent event, boolean feedback);

    EventResult collect(DomainEvent event, boolean feedback, int timeout);

    EventResult collect(DomainEvent event, boolean feedback, int timeout, TimeUnit timeUnit);

}
