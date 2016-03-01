package cheetah.engine;

import cheetah.async.AsynchronousPoolFactory;
import cheetah.common.Startable;
import cheetah.event.Event;
import cheetah.governor.Governor;
import cheetah.governor.GovernorFactory;
import cheetah.machine.Machine;
import cheetah.machine.MachineFactory;
import cheetah.mapper.Mapper;
import cheetah.worker.Worker;
import cheetah.worker.WorkerFactory;

/**
 * 事件处理引擎
 * Created by Max on 2016/2/19.
 */
public interface Engine extends Startable {

    /**
     * 分配应用事件的工作机器
     * @return
     */
    Machine assignApplicationEventMachine();

    /**
     * 分配领域事件的工作机器
     * @return
     */
    Machine assignDomainEventMachine();

    /**
     * 为每个事件分配一个管理者
     * @return
     */
    Governor assignGovernor();

    /**
     * 为每个事件分配一个管理者
     * @return
     */
    Governor assignGovernor(Event event);

    /**
     * 分配应用事件的工作者
     * @return
     */
    Worker assignWorker();

    void setWorkerFactory(WorkerFactory machineryFactory);

    void setMachineFactory(MachineFactory workerFactory);

    void setGovernorFactory(GovernorFactory governorFactory);

    void setMapper(Mapper mapper);

    /**
     * 设置异步者池子的工厂
     * @param asynchronousPoolFactory
     */
    void setAsynchronousPoolFactory(AsynchronousPoolFactory asynchronousPoolFactory);

    /**
     * 获取事件映射器
     * @return
     */
    Mapper getMapper();

    State state();

    default boolean isRunning() {
        return state().equals(State.RUNNING);
    }


    enum State {
        NEW, RUNNING, STOP
    }

}
