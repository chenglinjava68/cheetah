package cheetah.dispatcher.engine.support;

import cheetah.dispatcher.engine.Engine;
import cheetah.dispatcher.engine.EngineBuilder;
import cheetah.dispatcher.engine.EngineDirector;

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
        return engine;
    }
}
