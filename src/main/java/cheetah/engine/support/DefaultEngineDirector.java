package cheetah.engine.support;

import cheetah.engine.Engine;
import cheetah.engine.EngineBuilder;
import cheetah.engine.EngineDirector;

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
        engine.setWorkerFactory(builder.buildWorkerFactory());
        engine.setMachineFactory(builder.buildMachineFactory());
        engine.setGovernorFactory(builder.buildGovernorFactory());
        engine.setMapper(builder.buildMapper());
        return engine;
    }
}
