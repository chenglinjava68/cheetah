package org.cheetah.fighter.api;

import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.EventCollector;
import org.cheetah.fighter.EventResult;
import org.cheetah.ioc.BeanFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Max on 2016/1/10.
 */
public abstract class DomainEventPublisher {

    private static EventCollector collector = BeanFactory.getBean(EventCollector.class);

    private DomainEventPublisher() {
    }

    /**
     * 最为简单的发布，无法知道执行结果，disruptor和future都支持
     * @param event
     * @param <E>
     */
    public static <E extends DomainEvent> void publish(E event) {
        collector.collect(event);
    }

    /**
     * 发布后可以等待事件结果，需要将feedback设为true，如果需要知道事件消费执行的结果需要使用Future引擎，
     * 如果使用disruptor,得到的eventresult是一个无法知道后续情况的值，所以feedback和timeout将变得无意义
     * @param event
     * @param feedback  仅支持future引擎
     * @return
     */
    public static <E extends DomainEvent> EventResult publish(E event, boolean feedback) {
        return collector.collect(event, feedback);
    }
    /**
     * 发布后可以等待事件结果，需要将feedback设为true，并且支持消费者执行的超时时间设置，如果需要知道事件
     * 消费执行的结果需要使用Future引擎，如果使用disruptor,得到的eventresult是一个无法知道后续情况的值，
     * 所以feedback和timeout将变得无意义
     * @param event
     * @param feedback  仅支持future引擎
     * @param timeout   仅支持future引擎
     * @return
     */
    public static <E extends DomainEvent> EventResult publish(E event, boolean feedback, int timeout) {
        return collector.collect(event, feedback, timeout);
    }
    /**
     * 发布后可以等待事件结果，需要将feedback设为true，如果需要知道事件消费执行的结果需要使用Future引擎，
     * 如果使用disruptor,得到的eventresult是一个无法知道后续情况的值，所以feedback和timeout将变得无意义
     * @param event
     * @param feedback  仅支持future引擎
     * @param timeout   仅支持future引擎
     * @return
     */
    public static <E extends DomainEvent> EventResult publish(E event, boolean feedback, int timeout, TimeUnit timeUnit) {
        return collector.collect(event, feedback, timeout, timeUnit);
    }
}
