package cheetah.engine.support;

import cheetah.engine.EngineDirector;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 引擎的策略
 * Created by Max on 2016/2/23.
 */
public enum EnginePolicy {
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
    ORDINARY {
        @Override
        public EngineDirector getEngineDirector() {
            EngineDirector engineDirector = engineDirectorMap.get(this.name());
            if (Objects.nonNull(engineDirector)) {
                return engineDirector;
            }
            engineDirector = new OrdinaryEngineDirector(new OrdinaryEngineBuilder());
            engineDirectorMap.put(this.name(), engineDirector);
            return engineDirector;
        }
    },;

    private static final Map<String, EngineDirector> engineDirectorMap = new ConcurrentHashMap<>();

    public abstract EngineDirector getEngineDirector();

    public static EnginePolicy formatFrom(String name) {
        for (EnginePolicy policy : EnginePolicy.values()) {
            if (policy.name().equals(name.toUpperCase())) {
                return policy;
            }
        }
        throw new EnginePolicyNotFoundException();
    }
}
