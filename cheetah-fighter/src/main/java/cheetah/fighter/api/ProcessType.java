package cheetah.fighter.api;

/**
 * Created by Max on 2016/3/2.
 */
public enum ProcessType {
    ACTOR {
        @Override
        public String policy() {
            return "AKKA";
        }
    },
    DISRUPTOR {
        @Override
        public String policy() {
            return this.name();
        }
    },
    ORDINARY {
        @Override
        public String policy() {
            return this.name();
        }
    };

    public abstract String policy();
}
