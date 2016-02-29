package cheetah.engine;

import cheetah.common.Startable;
import cheetah.governor.Governor;
import cheetah.governor.GovernorFactory;
import cheetah.machine.Machine;
import cheetah.machine.MachineFactory;
import cheetah.mapper.Mapper;
import cheetah.worker.Worker;
import cheetah.worker.WorkerFactory;

/**
 * Created by Max on 2016/2/19.
 */
public interface Engine extends Startable {

    Machine assignApplicationEventMachine();

    Machine assignDomainEventMachine();

    Governor assignGovernor();

    Worker assignWorker();

    STATE getState();

    void setWorkerFactory(WorkerFactory machineryFactory);

    void setMachineFactory(MachineFactory workerFactory);

    void setGovernorFactory(GovernorFactory governorFactory);

    void setMapper(Mapper mapper);

    Mapper getMapper();

    default boolean isRunning() {
        return getState().equals(STATE.RUNNING);
    }

    enum STATE {
        NEW, RUNNING, STOP
    }

}
