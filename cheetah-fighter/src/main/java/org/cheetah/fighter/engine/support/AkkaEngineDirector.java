package org.cheetah.fighter.engine.support;

import org.cheetah.fighter.FighterConfig;
import org.cheetah.fighter.engine.Engine;
import org.cheetah.fighter.engine.EngineBuilder;
import org.cheetah.fighter.engine.EngineDirector;

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
        engine.setHandlerFactory(builder.buildHandlerFactory());
        engine.setMapping(builder.buildMapping());
        engine.setWorkerFactory(builder.buildWorkerFactory());
        engine.setAsynchronousPoolFactory(builder.buildAsynchronousPoolFactory(fighterConfig));
        return engine;
    }

    public void setFighterConfig(FighterConfig fighterConfig) {
        this.fighterConfig = fighterConfig;
    }

}
