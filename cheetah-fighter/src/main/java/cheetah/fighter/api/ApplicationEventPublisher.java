package cheetah.fighter.api;

import cheetah.fighter.container.BeanFactory;
import cheetah.fighter.event.ApplicationEvent;
import cheetah.fighter.event.EventCollector;

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

    public static <E extends ApplicationEvent> void publish(E event, boolean fisrtWin) {
        collector.collect(event, fisrtWin);
    }

    public static <E extends ApplicationEvent> void publish(boolean needResult, E event) {
        collector.collect(needResult, event);
    }

    public static <E extends ApplicationEvent> void publish(boolean needResult, boolean fisrtWin, E event) {
        collector.collect(needResult, fisrtWin, event);
    }


}
