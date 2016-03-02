package cheetah.engine.support;

import cheetah.engine.EngineDirector;

/**
 * 引擎的策略
 * Created by Max on 2016/2/23.
 */
public enum EnginePolicy {
    AKKA {
        @Override
        public EngineDirector getEngineDirector() {
            return new AkkaEngineDirector(new AkkaEngineBuilder());
        }
    },
    DISRUPTOR {
        @Override
        public EngineDirector getEngineDirector() {
            return new DisruptorEngineDirector(new DisruptorEngineBuilder());
        }
    };

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
