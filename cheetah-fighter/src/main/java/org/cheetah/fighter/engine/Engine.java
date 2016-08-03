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
import org.cheetah.fighter.worker.WorkerAdapter;
import org.cheetah.fighter.worker.WorkerAdapterFactory;
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

    /**
     * 分配worker适配器
     * @return
     */
    WorkerAdapter assignWorkerAdapter();

    /**
     * 获取异步工作者
     * @return
     */
    T getAsynchronous();

    /**
     * 设置worker适配器工厂
     * @return
     */
    void setWorkerAdapterFactory(WorkerAdapterFactory workerAdapterFactory);

    /**
     * 设置handler工厂
     * @param handlerFactory
     */
    void setHandlerFactory(HandlerFactory handlerFactory);

    @Deprecated
    void setMapping(HandlerMapping mapping);

    /**
     * 设置事件上下文
     * @param context
     */
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

    /**
     * 引擎状态
     * @return
     */
    State state();
    /**
     * 注册插件链
     */
    void registerPluginChain(PluginChain pluginChain);

    /**
     * 引擎是否启动
     * @return
     */
    boolean isRunning();

    /**
     * 引擎状态枚举
     */
    enum State {
        NEW, RUNNING, STOP
    }

}
