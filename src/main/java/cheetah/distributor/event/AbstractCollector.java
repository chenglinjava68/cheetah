package cheetah.distributor.event;


import cheetah.distributor.Regulator;

/**
 * Created by Max on 2016/2/3.
 */
public abstract class AbstractCollector implements Collector {
    private final Regulator regulator;

    public AbstractCollector(Regulator regulator) {
        this.regulator = regulator;
    }

    public Regulator getRegulator() {
        return regulator;
    }
}
