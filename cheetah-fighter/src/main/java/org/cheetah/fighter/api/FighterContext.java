package org.cheetah.fighter.api;

import org.cheetah.fighter.core.event.Callback;
import org.cheetah.fighter.core.event.Event;
import org.cheetah.fighter.core.event.EventCollector;
import org.cheetah.ioc.BeanFactory;

/**
 * Created by Max on 2016/4/29.
 */
public final class FighterContext {
    private static EventCollector collector = BeanFactory.getBean(EventCollector.class);

    public static <E extends Event> void publish(E event) {
        collector.collect(event);
    }

    public static <E extends Event> void publish(E event, Callback callback) {
        collector.collect(event, callback);
    }
}

