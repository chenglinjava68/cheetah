package cheetah.api;

import cheetah.container.BeanFactory;
import cheetah.event.DomainEvent;
import cheetah.event.EventCollector;

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

    public static <E extends DomainEvent> void publish(E event, boolean fisrtWin) {
        collector.collect(event, fisrtWin);
    }

    public static <E extends DomainEvent> void publish(boolean needResult, E event) {
        collector.collect(needResult, event);
    }

    public static <E extends DomainEvent> void publish(boolean needResult, boolean fisrtWin, E event) {
        collector.collect(needResult, fisrtWin, event);
    }

    public static <E extends DomainEvent> void publish(E event, ProcessType processType) {
        collector.collect(event, processType);
    }

    public static <E extends DomainEvent> void publish(E event, boolean fisrtWin, ProcessType processType) {
        collector.collect(event, fisrtWin, processType);
    }

    public static <E extends DomainEvent> void publish(boolean needResult, E event, ProcessType processType) {
        collector.collect(needResult, event, processType);
    }

    public static <E extends DomainEvent> void publish(boolean needResult, boolean fisrtWin, E event, ProcessType processType) {
        collector.collect(needResult, fisrtWin, event, processType);
    }

}
