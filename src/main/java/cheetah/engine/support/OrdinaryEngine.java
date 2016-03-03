package cheetah.engine.support;

import cheetah.engine.AbstractEngine;
import cheetah.governor.Governor;
import cheetah.governor.support.OrdinaryGovernor;
import cheetah.worker.Worker;

import java.util.Objects;

/**
 * Created by Max on 2016/2/1.
 */
public class OrdinaryEngine extends AbstractEngine {

    public OrdinaryEngine() {
        this.state = State.NEW;
    }

    @Override
    public Governor assignGovernor() {
        if (Objects.isNull(governor())) {
            Governor governor = governorFactory().createGovernor();
            ((OrdinaryGovernor) governor).setWorker((Worker) asynchronousPoolFactory().getAsynchronous());
            governor.registerMachineSquad(context().getHandlers());
            setGovernor(governor);
            return governor;
        } else {
            try {
                Governor clone = governor().kagebunsin();
                clone.reset();
                Worker worker = (Worker) asynchronousPoolFactory().getAsynchronous();
                ((OrdinaryGovernor) clone).setWorker(worker);
                return clone;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                Governor governor = governorFactory().createGovernor();
                ((OrdinaryGovernor) governor).setWorker((Worker) asynchronousPoolFactory().getAsynchronous());
                return governor;
            }
        }
    }

}
