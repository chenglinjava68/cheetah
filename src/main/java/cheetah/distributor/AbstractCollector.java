package cheetah.distributor;


/**
 * Created by Max on 2016/2/3.
 */
public abstract class AbstractCollector implements Collector {
    private final Conveyor conveyor;

    public AbstractCollector(Conveyor conveyor) {
        this.conveyor = conveyor;
    }

    protected Conveyor getConveyor() {
        return conveyor;
    }

}
