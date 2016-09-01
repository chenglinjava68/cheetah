package org.cheetah.fighter.engine.support;

import org.cheetah.commons.logger.Info;
import org.cheetah.fighter.api.FighterConfig;
import org.cheetah.fighter.engine.Engine;
import org.cheetah.fighter.engine.EngineBuilder;
import org.cheetah.fighter.engine.EngineDirector;

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
    public Engine directEngine() {
        Engine engine = new DisruptorEngine();
        engine.setHandlerFactory(builder.buildHandlerFactory());
        engine.setWorkerAdapterFactory(builder.buildWorkerAdapterFactory());
        engine.setAsynchronousPoolFactory(builder.buildAsynchronousPoolFactory(configuration));
        Info.log(this.getClass(), "direct disruptor engine with handler factory and worker factroy and asynchronous pool factory");
        return engine;
    }

    public void setFighterConfig(FighterConfig fighterConfig) {
        this.configuration = fighterConfig;
    }
}
