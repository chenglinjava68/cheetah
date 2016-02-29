package cheetah.engine.support;

import akka.actor.ActorRef;
import cheetah.async.AsynchronousPoolFactory;
import cheetah.async.akka.ActorFactory;
import cheetah.async.akka.ActorPoolFactory;
import cheetah.core.Configuration;
import cheetah.engine.EngineBuilder;
import cheetah.governor.GovernorFactory;
import cheetah.governor.support.AkkaGovernorFactory;
import cheetah.machine.MachineFactory;
import cheetah.machine.support.DefaultMachineFactory;
import cheetah.mapper.Mapper;
import cheetah.mapper.support.MachineMapper;
import cheetah.worker.WorkerFactory;
import cheetah.worker.support.AkkaWorkerFactory;

/**
 * Created by Max on 2016/2/19.
 */
public class DefualtEngineBuilder implements EngineBuilder {

    @Override
    public MachineFactory buildMachineFactory() {
        return new DefaultMachineFactory();
    }

    @Override
    public GovernorFactory buildGovernorFactory() {
        return new AkkaGovernorFactory();
    }

    @Override
    public WorkerFactory buildWorkerFactory() {
        return new AkkaWorkerFactory();
    }

    @Override
    public Mapper buildMapper() {
        return new MachineMapper();
    }

    @Override
    public AsynchronousPoolFactory buildAsynchronousPoolFactory(Configuration configuration) {
        ActorFactory actorFactory = new ActorFactory();
        if(configuration.eventPerformerSize() > 0)
            actorFactory.setActorSize(configuration.eventPerformerSize());
        AsynchronousPoolFactory<ActorRef> factory = new ActorPoolFactory(actorFactory);
        return factory;
    }

}
