package cheetah.distributor.engine;

import cheetah.distributor.governor.GovernorFactory;
import cheetah.distributor.machinery.MachineryFactory;
import cheetah.distributor.worker.WorkerFactory;

/**
 * Created by Max on 2016/2/19.
 */
public interface EngineBuilder {
    WorkerFactory buildWorkerFactory();

    GovernorFactory buildGovernorFactory();

    MachineryFactory buildMachineryFactory();
}
