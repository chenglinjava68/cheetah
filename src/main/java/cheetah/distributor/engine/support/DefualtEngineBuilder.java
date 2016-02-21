package cheetah.distributor.engine.support;

import cheetah.distributor.engine.EngineBuilder;
import cheetah.distributor.governor.GovernorFactory;
import cheetah.distributor.governor.support.AkkaGovernorFactory;
import cheetah.distributor.machinery.MachineryFactory;
import cheetah.distributor.machinery.support.AkkaMachineryFactory;
import cheetah.distributor.worker.WorkerFactory;
import cheetah.distributor.worker.support.AkkaWorkerFactory;

/**
 * Created by Max on 2016/2/19.
 */
public class DefualtEngineBuilder implements EngineBuilder {

    @Override
    public WorkerFactory buildWorkerFactory() {
        return new AkkaWorkerFactory();
    }

    @Override
    public GovernorFactory buildGovernorFactory() {
        return new AkkaGovernorFactory();
    }

    @Override
    public MachineryFactory buildMachineryFactory() {
        return new AkkaMachineryFactory();
    }
}
