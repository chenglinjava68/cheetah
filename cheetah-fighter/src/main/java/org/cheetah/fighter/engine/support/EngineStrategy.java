package org.cheetah.fighter.engine.support;

import org.cheetah.fighter.engine.EngineDirector;
import org.cheetah.fighter.engine.EnginePolicyNotFoundException;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 引擎的策略
 * Created by Max on 2016/2/23.
 */
public enum EngineStrategy {
    AKKA {
        @Override
        public EngineDirector getEngineDirector() {
            EngineDirector engineDirector = engineDirectorMap.get(this.name());
            if (Objects.nonNull(engineDirector)) {
                return engineDirector;
            }
            engineDirector = new AkkaEngineDirector(new AkkaEngineBuilder());
            engineDirectorMap.put(this.name(), engineDirector);
            return engineDirector;
        }
    },
    DISRUPTOR {
        @Override
        public EngineDirector getEngineDirector() {
            EngineDirector engineDirector = engineDirectorMap.get(this.name());
            if (Objects.nonNull(engineDirector)) {
                return engineDirector;
            }
            engineDirector = new DisruptorEngineDirector(new DisruptorEngineBuilder());
            engineDirectorMap.put(this.name(), engineDirector);
            return engineDirector;
        }
    },
    FUTURE {
        @Override
        public EngineDirector getEngineDirector() {
            EngineDirector engineDirector = engineDirectorMap.get(this.name());
            if (Objects.nonNull(engineDirector)) {
                return engineDirector;
            }
            engineDirector = new ForeseeableEngineDirector(new ForeseeableEngineBuilder());
            engineDirectorMap.put(this.name(), engineDirector);
            return engineDirector;
        }
    };

    private static final Map<String, EngineDirector> engineDirectorMap = new ConcurrentHashMap<>();

    public abstract EngineDirector getEngineDirector();

    public static EngineStrategy getEngine(String name) {
        try {
            return EngineStrategy.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new EnginePolicyNotFoundException(e);
        }
    }

}
