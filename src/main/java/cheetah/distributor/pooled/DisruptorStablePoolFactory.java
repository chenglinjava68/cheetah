package cheetah.distributor.pooled;

/**
 * Created by Max on 2015/12/28.
 */
public class DisruptorStablePoolFactory implements StablePoolFactory {
    private DisruptorStableFactory disruptorStableFactory;
    @Override
    public StablePool getStablePool() {
        return null;
    }

    @Override
    public void setStableFactory(StableFactory stableFactory) {
        disruptorStableFactory = (DisruptorStableFactory) stableFactory;
    }

    public void setDisruptorStableFactory(DisruptorStableFactory disruptorStableFactory) {
        this.disruptorStableFactory = disruptorStableFactory;
    }
}
