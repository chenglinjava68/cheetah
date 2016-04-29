package org.cheetah.fighter.api;

import org.cheetah.fighter.container.BeanFactory;
import org.cheetah.fighter.event.ApplicationEvent;
import org.cheetah.fighter.event.Callback;
import org.cheetah.fighter.event.EventCollector;

/**
 * Created by Max on 2016/1/10.
 */
public abstract class ApplicationEventPublisher {
    private static EventCollector collector = BeanFactory.getBean(EventCollector.class);

    private ApplicationEventPublisher() {
    }

    public static <E extends ApplicationEvent> void publish(E event) {
        collector.collect(event);
    }

    public static <E extends ApplicationEvent> void publish(E event, Callback callback) {
        collector.collect(event, callback);
    }

}
