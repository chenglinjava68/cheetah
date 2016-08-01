package org.cheetah.fighter.api;

import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.EventCollector;
import org.cheetah.fighter.EventResult;
import org.cheetah.ioc.BeanFactory;

import java.util.concurrent.TimeUnit;

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

    public static <E extends DomainEvent> EventResult publish(E event, boolean feedback) {
        return collector.collect(event, feedback);
    }

    public static <E extends DomainEvent> EventResult publish(E event, boolean feedback, int timeout) {
        return collector.collect(event, feedback, timeout);
    }

    public static <E extends DomainEvent> EventResult publish(E event, boolean feedback, int timeout, TimeUnit timeUnit) {
        return collector.collect(event, feedback, timeout, timeUnit);
    }
}
