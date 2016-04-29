package org.cheetah.fighter.api;

import org.cheetah.fighter.container.BeanFactory;
import org.cheetah.fighter.event.*;

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
