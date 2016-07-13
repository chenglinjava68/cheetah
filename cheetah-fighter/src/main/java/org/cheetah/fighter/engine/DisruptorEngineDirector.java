package org.cheetah.fighter.engine;

import org.cheetah.fighter.core.FighterConfig;
import org.cheetah.fighter.core.eventbus.EventBus;
import org.cheetah.fighter.core.eventbus.EngineBuilder;
import org.cheetah.fighter.core.eventbus.EngineDirector;

/**
 * Created by Max on 2016/3/2.
 */
public class DisruptorEngineDirector implements EngineDirector {
    private FighterConfig configuration;
    private EngineBuilder builder;

    public DisruptorEngineDirector(EngineBuilder builder) {
        this.builder = builder;
    }

    @Override
    public EventBus directEngine() {
        EventBus engine = new DisruptorEngine();
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
