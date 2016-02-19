package cheetah.distributor;

import cheetah.container.spring.SpringBeanFactory;
import cheetah.event.ApplicationEvent;

/**
 * Created by Max on 2016/1/10.
 */
public abstract class ApplicationEventEmitter {
    private static ApplicationEventCollector collector = SpringBeanFactory.getBean(ApplicationEventCollector.class);

    private ApplicationEventEmitter() {
    }

    public static <E extends ApplicationEvent> void launch(E event) {
        collector.collect(event);
    }

    public static <E extends ApplicationEvent> void launch(E event, int mode) {
        collector.collect(event, mode);
    }

}
