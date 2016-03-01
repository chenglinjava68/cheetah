package cheetah.async;

import cheetah.common.Startable;
import cheetah.event.Event;
import cheetah.mapper.Mapper;

/**
 * 异步工作者池子的工厂
 * Created by Max on 2016/2/29.
 */
public interface AsynchronousPoolFactory<T> extends Startable {
    /**
     * 获取一个异步工作者
     * @param event
     * @return
     */
    T getAsynchronous(Event event);

    /**
     * 设置事件的映射器
     * @param mapper
     */
    void setMapper(Mapper mapper);

    void setAsynchronousFactory(AsynchronousFactory asynchronousFactory);
}
