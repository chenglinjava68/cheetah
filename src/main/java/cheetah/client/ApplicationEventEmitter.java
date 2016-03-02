package cheetah.client;

import cheetah.container.BeanFactory;
import cheetah.event.ApplicationEvent;
import cheetah.event.Collector;

/**
 * Created by Max on 2016/1/10.
 */
public abstract class ApplicationEventEmitter {
    private static Collector collector = BeanFactory.getBeanFactory().getBean(Collector.class);

    private ApplicationEventEmitter() {
    }

    public static <E extends ApplicationEvent> void launch(E event) {
        collector.collect(event);
    }

    public static <E extends ApplicationEvent> void launch(E event, boolean fisrtWin) {
        collector.collect(event, fisrtWin);
    }

    public static <E extends ApplicationEvent> void launch(boolean needResult, E event) {
        collector.collect(needResult, event);
    }

    public static <E extends ApplicationEvent> void launch(boolean needResult, boolean fisrtWin, E event) {
        collector.collect(needResult, fisrtWin, event);
    }

    public static <E extends ApplicationEvent> void launch(E event, ProcessType processType) {
        collector.collect(event, processType);
    }

    public static <E extends ApplicationEvent> void launch(E event, boolean fisrtWin, ProcessType processType) {
        collector.collect(event, fisrtWin, processType);
    }

    public static <E extends ApplicationEvent> void launch(boolean needResult, E event, ProcessType processType) {
        collector.collect(needResult, event, processType);
    }

    public static <E extends ApplicationEvent> void launch(boolean needResult, boolean fisrtWin, E event, ProcessType processType) {
        collector.collect(needResult, fisrtWin, event, processType);
    }
}
