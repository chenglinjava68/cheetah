package org.cheetah.fighter.api;

import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.EventCollector;
import org.cheetah.fighter.EventResult;
import org.cheetah.ioc.BeanFactory;

/**
 * Created by Max on 2016/4/29.
 */
public final class FighterContext {
    private static EventCollector collector = BeanFactory.getBean(EventCollector.class);

    public static <E extends DomainEvent> void publish(E event) {
        collector.collect(event);
    }

    public static <E extends DomainEvent> EventResult publish(E event, boolean feedback) {
        return collector.collect(event, feedback);
    }

}

