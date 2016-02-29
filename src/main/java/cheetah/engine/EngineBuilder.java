package cheetah.engine;

import cheetah.governor.GovernorFactory;
import cheetah.machine.MachineFactory;
import cheetah.mapper.Mapper;
import cheetah.worker.WorkerFactory;

/**
 * Created by Max on 2016/2/19.
 */
public interface EngineBuilder {
    MachineFactory buildMachineFactory();

    GovernorFactory buildGovernorFactory();

    WorkerFactory buildWorkerFactory();

    Mapper buildMapper();
}
