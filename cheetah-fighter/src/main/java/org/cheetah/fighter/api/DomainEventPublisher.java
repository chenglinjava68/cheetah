package org.cheetah.fighter.api;

import org.cheetah.fighter.core.event.DomainEvent;
import org.cheetah.fighter.core.event.EventCollector;
import org.cheetah.ioc.BeanFactory;

/**
 * Created by Max on 2016/1/10.
 */
public abstract class DomainEventPublisher {

    private static EventCollector collector = BeanFactory.getBean(EventCollector.class);

    private DomainEventPublisher() {
    }

    public static <E extends DomainEvent> void publish(E event) {
        collector.collect(event);
    }

}
