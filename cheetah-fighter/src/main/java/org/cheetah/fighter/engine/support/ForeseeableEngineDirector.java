package org.cheetah.fighter.engine.support;

import org.cheetah.commons.logger.Info;
import org.cheetah.fighter.FighterConfig;
import org.cheetah.fighter.engine.Engine;
import org.cheetah.fighter.engine.EngineBuilder;
import org.cheetah.fighter.engine.EngineDirector;

/**
 * Created by Max on 2016/3/2.
 */
public class ForeseeableEngineDirector implements EngineDirector {
    private FighterConfig configuration;
    private EngineBuilder builder;

    public ForeseeableEngineDirector(EngineBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Engine directEngine() {
        Engine engine = new ForeseeableEngine();
        engine.setHandlerFactory(builder.buildHandlerFactory());
        engine.setWorkerAdapterFactory(builder.buildWorkerAdapterFactory());
        engine.setAsynchronousPoolFactory(builder.buildAsynchronousPoolFactory(configuration));
        Info.log(this.getClass(), "direct foreseeable engine with handler factory and worker factroy and asynchronous pool factory");
        return engine;
    }

    public void setFighterConfig(FighterConfig fighterConfig) {
        this.configuration = fighterConfig;
    }
}
