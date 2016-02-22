package cheetah.distributor.core.support;

import cheetah.container.BeanFactory;
import cheetah.distributor.event.Collector;
import cheetah.distributor.event.DomainEvent;

/**
 * Created by Max on 2016/1/10.
 */
public abstract class DomainEventEmitter {

    private static Collector collector = BeanFactory.getBeanFactory().getBean(Collector.class);

    private DomainEventEmitter() {
    }

    public static <E extends DomainEvent> void launch(E event) {
        collector.collect(event);
    }

    public static <E extends DomainEvent> void launch(E event, boolean fisrtWin) {
        collector.collect(event, fisrtWin);
    }

    public static <E extends DomainEvent> void launch(boolean needResult, E event) {
        collector.collect(needResult, event);
    }

    public static <E extends DomainEvent> void launch(boolean needResult, boolean fisrtWin, E event) {
        collector.collect(needResult, fisrtWin, event);
    }


}
