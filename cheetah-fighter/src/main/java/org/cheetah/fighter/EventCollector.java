package org.cheetah.fighter;

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
     *  收集一个事件
     * @param event
     * @param feedback  事件消费后的反馈，如果是true就会得到一个有用的EventResul，里面能够准确的反馈出消费者消费的情况
     *                  如果是false得到的EventResult并不能准确的反馈出消费者消费完毕的情况，不能准确知道是否消费成功
     * @return
     */
    EventResult collect(DomainEvent event, boolean feedback);

    /**
     * 收集一个事件
     * @param event
     * @param feedback 事件消费后的反馈，如果是true就会得到一个有用的EventResul，里面能够准确的反馈出消费者消费的情况
     *                  如果是false得到的EventResult并不能准确的反馈出消费者消费完毕的情况，不能准确知道是否消费成功
     * @param timeout
     * @return
     */
    EventResult collect(DomainEvent event, boolean feedback, int timeout);

    /**
     * 收集一个事件
     * @param event
     * @param feedback 事件消费后的反馈，如果是true就会得到一个有用的EventResul，里面能够准确的反馈出消费者消费的情况
     *                  如果是false得到的EventResult并不能准确的反馈出消费者消费完毕的情况，不能准确知道是否消费成功
     * @param timeout  消费的超时时间
     * @param timeUnit 消费的超时时间的单位类型
     * @return
     */
    EventResult collect(DomainEvent event, boolean feedback, int timeout, TimeUnit timeUnit);

}
