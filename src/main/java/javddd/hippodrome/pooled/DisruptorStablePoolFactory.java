package javddd.hippodrome.pooled;

import javddd.hippodrome.DisruptorStableFactory;
import javddd.hippodrome.StableFactory;
import javddd.hippodrome.StablePool;
import javddd.hippodrome.StablePoolFactory;

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
