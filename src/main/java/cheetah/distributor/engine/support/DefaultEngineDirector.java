package cheetah.distributor.engine.support;

import cheetah.distributor.engine.Engine;
import cheetah.distributor.engine.EngineBuilder;
import cheetah.distributor.engine.EngineDirector;

/**
 * Created by Max on 2016/2/19.
 */
public class DefaultEngineDirector implements EngineDirector {
    private EngineBuilder builder;

    public DefaultEngineDirector(EngineBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Engine directEngine() {
        Engine engine = new DefaultEngine();
        engine.setMachineFactory(builder.buildWorkerFactory());
        engine.setMachineFactory(builder.buildMachineFactory());
        engine.setGovernorFactory(builder.buildGovernorFactory());
        return engine;
    }
}
