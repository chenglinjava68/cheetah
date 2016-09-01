package org.cheetah.fighter.api;

import java.util.concurrent.TimeUnit;

/**
 * 事件收集器
 * Created by Max on 2016/2/1.
*/
public interface EventCollector {
    /**
     * 收集一个事件
     * @param event
     */
    void collect(DomainEvent event);

    /**
     *  收集一个事件，如果需要得到一个消费准确的情况，则需要使用Future引擎，如果使用disruptor,得到的eventresult是一个无法
     * 知道后续情况的值，所以feedback将变得无意义，等同于没有设置
     * @param event     用户自定义的领域事件对象
     * @param feedback  事件消费后的反馈，如果是true就会得到一个有用的EventResul，里面能够准确的反馈出消费者消费的情况
     *                  如果是false得到的EventResult并不能准确的反馈出消费者消费完毕的情况，不能准确知道是否消费成功
     * @return
     */
    EventResult collect(DomainEvent event, boolean feedback);

    /**
     * 收集一个事件，如果需要得到一个消费准确的情况，则需要使用Future引擎，如果使用disruptor,得到的eventresult是一个无法
     * 知道后续情况的值，所以feedback和timeout将变得无意义，等同于没有设置
     * @param event     用户自定义的领域事件对象
     * @param feedback 事件消费后的反馈，如果是true就会得到一个有用的EventResul，里面能够准确的反馈出消费者消费的情况
     *                  如果是false得到的EventResult并不能准确的反馈出消费者消费完毕的情况，不能准确知道是否消费成功
     * @param timeout   消费者执行超时时间，单位为秒，仅支持future引擎
     * @return
     */
    EventResult collect(DomainEvent event, boolean feedback, int timeout);

    /**
     * 收集一个事件，如果需要得到一个消费准确的情况，则需要使用Future引擎，如果使用disruptor,得到的eventresult是一个无法
     * 知道后续情况的值，所以feedback和timeout将变得无意义，等同于没有设置
     *
     * @param event     用户自定义的领域事件对象
     * @param feedback 事件消费后的反馈，如果是true就会得到一个有用的EventResul，里面能够准确的反馈出消费者消费的情况
     *                  如果是false得到的EventResult并不能准确的反馈出消费者消费完毕的情况，不能准确知道是否消费成功
     * @param timeout  消费者执行超时时间，仅支持future引擎
     * @param timeUnit 消费的超时时间的单位类型
     * @return
     */
    EventResult collect(DomainEvent event, boolean feedback, int timeout, TimeUnit timeUnit);

}
