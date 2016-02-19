package cheetah.distributor.core;

import cheetah.container.spring.SpringBeanFactory;
import cheetah.distributor.event.DomainEvent;

/**
 * Created by Max on 2016/1/10.
 */
public abstract class DomainEventEmitter {

    private static DomainEventCollector collector= SpringBeanFactory.getBean(DomainEventCollector.class);

    private DomainEventEmitter() {
    }

    public static <E extends DomainEvent> void launch(E event) {
        collector.collect(event);
    }

    public static <E extends DomainEvent> void launch(E event, int mode) {
        collector.collect(event, mode);
    }


}
