package cheetah.engine.support;

import cheetah.engine.EngineDirector;

/**
 * 引擎的策略  默认使用akka的引擎策略
 * Created by Max on 2016/2/23.
 */
public enum EnginePolicy {
    DEFAULT {
        @Override
        public EngineDirector getEngineDirector() {
            return new DefaultEngineDirector(new DefualtEngineBuilder());
        }
    },
    DISRUPTOR {
        @Override
        public EngineDirector getEngineDirector() {
            return null;
        }
    };

    public abstract EngineDirector getEngineDirector();

    public static EnginePolicy formatFrom(String name) {
        for (EnginePolicy policy : EnginePolicy.values()) {
            if (policy.name().equals(name.toUpperCase())) {
                return policy;
            }
        }
        return DEFAULT;
    }
}
