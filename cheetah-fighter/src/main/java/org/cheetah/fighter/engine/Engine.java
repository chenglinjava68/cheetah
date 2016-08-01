package org.cheetah.fighter.engine;

import org.cheetah.common.Startable;
import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.DomainEventListener;
import org.cheetah.fighter.EventContext;
import org.cheetah.fighter.HandlerMapping;
import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.handler.HandlerFactory;
import org.cheetah.fighter.plugin.PluginChain;
import org.cheetah.fighter.worker.WorkerFactory;


/**
 * 事件处理引擎
 * Created by Max on 2016/2/19.
 */
public interface Engine<T> extends Startable {

    /**
     * 分配领域事件的工作处理器
     * @return
     */
    Handler assignDomainEventHandler(DomainEventListener<DomainEvent> eventListener);

    WorkerFactory getWorkerFactory();

    /**
     * 为每个事件分配一个管理者
     * @return
     */
    T getAsynchronous();

    void setWorkerFactory(WorkerFactory workerFactory);

    void setHandlerFactory(HandlerFactory handlerFactory);

    void setMapping(HandlerMapping mapping);

    void setContext(EventContext context);

    /**
     * 设置异步者池子的工厂
     * @param asynchronousPoolFactory
     */
    void setAsynchronousPoolFactory(AsynchronousPoolFactory asynchronousPoolFactory);

    /**
     * 获取事件映射器
     * @return
     */
    @Deprecated
    HandlerMapping getMapping();

    State state();

    void registerPluginChain(PluginChain pluginChain);

    boolean isRunning();


    enum State {
        NEW, RUNNING, STOP
    }

}
