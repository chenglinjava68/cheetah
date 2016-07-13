package org.cheetah.fighter.engine;

import org.cheetah.fighter.core.FighterConfig;
import org.cheetah.fighter.core.engine.Engine;
import org.cheetah.fighter.core.engine.EngineBuilder;
import org.cheetah.fighter.core.engine.EngineDirector;

/**
 * Created by Max on 2016/3/2.
 */
public class OrdinaryEngineDirector implements EngineDirector {
    private FighterConfig configuration;
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

    public void setFighterConfig(FighterConfig fighterConfig) {
        this.configuration = fighterConfig;
    }
}
