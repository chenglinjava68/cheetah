package org.cheetah.fighter.api;

import org.cheetah.fighter.container.BeanFactory;
import org.cheetah.fighter.event.DomainEvent;
import org.cheetah.fighter.event.EventCollector;

/**
 * Created by Max on 2016/1/10.
 */
public abstract class DomainEvenPublisher {

    private static EventCollector collector = BeanFactory.getBean(EventCollector.class);

    private DomainEvenPublisher() {
    }

    public static <E extends DomainEvent> void publish(E event) {
        collector.collect(event);
    }

    public static <E extends DomainEvent> void publish(E event, boolean fisrtWin) {
        collector.collect(event, fisrtWin);
    }

    public static <E extends DomainEvent> void publish(boolean needResult, E event) {
        collector.collect(needResult, event);
    }

    public static <E extends DomainEvent> void publish(boolean needResult, boolean fisrtWin, E event) {
        collector.collect(needResult, fisrtWin, event);
    }

}
