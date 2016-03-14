package cheetah.engine.support;

import cheetah.core.Configuration;
import cheetah.engine.Engine;
import cheetah.engine.EngineBuilder;
import cheetah.engine.EngineDirector;

/**
 * Created by Max on 2016/3/2.
 */
public class OrdinaryEngineDirector implements EngineDirector {
    private Configuration configuration;
    private EngineBuilder builder;

    public OrdinaryEngineDirector(EngineBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Engine directEngine() {
        Engine engine = new OrdinaryEngine();
        engine.setWorkerFactory(builder.buildWorkerFactory());
        engine.setHandlerFactory(builder.buildHandlerFactory());
        engine.setGovernorFactory(builder.buildGovernorFactory());
        engine.setMapping(builder.buildMapping());
        engine.setAsynchronousPoolFactory(builder.buildAsynchronousPoolFactory(configuration));
        return engine;
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
