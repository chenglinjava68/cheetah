package org.cheetah.fighter.core.eventbus;

import org.cheetah.commons.Startable;
import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.core.EventContext;
import org.cheetah.fighter.core.HandlerMapping;
import org.cheetah.fighter.core.governor.Governor;
import org.cheetah.fighter.core.governor.GovernorFactory;
import org.cheetah.fighter.core.handler.Handler;
import org.cheetah.fighter.core.handler.HandlerFactory;
import org.cheetah.fighter.core.worker.Worker;
import org.cheetah.fighter.core.worker.WorkerFactory;
import org.cheetah.fighter.plugin.PluginChain;


/**
 * 事件处理引擎
 * Created by Max on 2016/2/19.
 */
public interface EventBus extends Startable {

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

    boolean isRunning();


    enum State {
        NEW, RUNNING, STOP
    }

}
