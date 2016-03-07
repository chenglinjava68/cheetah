package cheetah.engine;

import cheetah.core.async.AsynchronousPoolFactory;
import cheetah.common.Startable;
import cheetah.core.plugin.PluginChain;
import cheetah.core.EventContext;
import cheetah.governor.Governor;
import cheetah.governor.GovernorFactory;
import cheetah.handler.Handler;
import cheetah.handler.HandlerFactory;
import cheetah.mapping.HandlerMapping;
import cheetah.worker.Worker;
import cheetah.worker.WorkerFactory;


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
