package cheetah.distributor.engine;

import cheetah.distributor.governor.GovernorFactory;
import cheetah.distributor.worker.WorkerFactory;
import cheetah.distributor.machine.MachineFactory;

/**
 * Created by Max on 2016/2/19.
 */
public interface EngineBuilder {
    MachineFactory buildMachineFactory();

    GovernorFactory buildGovernorFactory();

    WorkerFactory buildWorkerFactory();
}
