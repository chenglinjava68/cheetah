package org.cheetah.fighter.engine;

import org.cheetah.fighter.core.FighterConfig;
import org.cheetah.fighter.core.engine.Engine;
import org.cheetah.fighter.core.engine.EngineBuilder;
import org.cheetah.fighter.core.engine.EngineDirector;

/**
 * Created by Max on 2016/2/19.
 */
public class AkkaEngineDirector implements EngineDirector {
    private EngineBuilder builder;
    private FighterConfig fighterConfig;

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
        engine.setAsynchronousPoolFactory(builder.buildAsynchronousPoolFactory(fighterConfig));
        return engine;
    }

    public void setFighterConfig(FighterConfig fighterConfig) {
        this.fighterConfig = fighterConfig;
    }

}
