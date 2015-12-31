package jvddd.hippodrome;

/**
 * Created by Max on 2015/12/23.
 */
public interface StablePoolFactory {
    StablePool getStablePool();

    void setStableFactory(StableFactory stableFactory);
}
