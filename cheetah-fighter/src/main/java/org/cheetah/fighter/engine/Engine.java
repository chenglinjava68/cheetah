package org.cheetah.fighter.engine;

import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.commons.Startable;
import org.cheetah.fighter.core.EventContext;
import org.cheetah.fighter.plugin.PluginChain;
import org.cheetah.fighter.governor.Governor;
import org.cheetah.fighter.governor.GovernorFactory;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.handler.HandlerFactory;
import org.cheetah.fighter.mapping.HandlerMapping;
import org.cheetah.fighter.worker.Worker;
import org.cheetah.fighter.worker.WorkerFactory;


/**
 * 事件处理引擎
 * Created by Max on 2016/2/19.
 */
public interface Engine extends Startable {

    /**
     * 分配应用事件的工作处理器
     * @return
     */
    Handler assignApplicationEventHandler();

    /**
     * 分配领域事件的工作处理器
     * @return
     */
    Handler assignDomainEventHandler();

    /**
     * 为每个事件分配一个管理者
     * @return
     */
    Governor assignGovernor();

    /**
     * 分配应用事件的工作者
     * @return
     */
    Worker assignWorker();

    void setWorkerFactory(WorkerFactory workerFactory);

    void setHandlerFactory(HandlerFactory handlerFactory);

    void setGovernorFactory(GovernorFactory governorFactory);

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
    HandlerMapping getMapping();

    State state();

    void registerPluginChain(PluginChain pluginChain);

    default boolean isRunning() {
        return state().equals(State.RUNNING);
    }


    enum State {
        NEW, RUNNING, STOP
    }

}
