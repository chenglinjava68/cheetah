package cheetah.fighter.engine.support;

import cheetah.fighter.core.Configuration;
import cheetah.fighter.engine.Engine;
import cheetah.fighter.engine.EngineBuilder;
import cheetah.fighter.engine.EngineDirector;

/**
 * Created by Max on 2016/2/19.
 */
public class AkkaEngineDirector implements EngineDirector {
    private EngineBuilder builder;
    private Configuration configuration;

    public AkkaEngineDirector(EngineBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Engine directEngine() {
        Engine engine = new AkkaEngine();
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
