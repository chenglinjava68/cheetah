package cheetah.engine.support;

import cheetah.core.Configuration;
import cheetah.engine.Engine;
import cheetah.engine.EngineBuilder;
import cheetah.engine.EngineDirector;

/**
 * Created by Max on 2016/2/19.
 */
public class DefaultEngineDirector implements EngineDirector {
    private EngineBuilder builder;
    private Configuration configuration;

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
        engine.setAsynchronousPoolFactory(builder.buildAsynchronousPoolFactory(configuration));
        return engine;
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

}
