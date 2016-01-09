package cheetah.hippodrome;

import cheetah.hippodrome.pooled.DisruptorStablePoolFactory;

/**
 * Created by Max on 2015/12/28.
 */
public class DisruptorStableFactory implements StableFactory {
    private Stable stable;
    private StablePoolFactory stablePoolFactory;

    public DisruptorStableFactory() {
        this.stablePoolFactory = new DisruptorStablePoolFactory();
        this.stablePoolFactory.setStableFactory(this);
    }

    public DisruptorStableFactory(Stable stable, StablePoolFactory stablePoolFactory) {
        this.stable = stable;
        this.stablePoolFactory = stablePoolFactory;
    }

    @Override
    public Stable getStable() {
        return null;
    }
}
