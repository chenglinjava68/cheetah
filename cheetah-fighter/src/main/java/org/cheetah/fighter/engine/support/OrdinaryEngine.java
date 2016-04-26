package org.cheetah.fighter.engine.support;

import org.cheetah.fighter.engine.AbstractEngine;
import org.cheetah.fighter.governor.Governor;
import org.cheetah.fighter.governor.support.OrdinaryGovernor;
import org.cheetah.fighter.governor.support.OrdinaryGovernorAdapter;
import org.cheetah.fighter.worker.Worker;

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
            governor = new OrdinaryGovernorAdapter((OrdinaryGovernor) governor, pluginChain());
            ((OrdinaryGovernorAdapter) governor).setWorker((Worker) asynchronousPoolFactory().getAsynchronous());
            governor.registerHandlerSquad(context().handlers());
            setGovernor(governor);
            return governor;
        } else {
            try {
                Governor clone = governor().kagebunsin();
                clone.reset();
                Worker worker = (Worker) asynchronousPoolFactory().getAsynchronous();
                ((OrdinaryGovernorAdapter) clone).setWorker(worker);
                return clone;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                Governor governor = governorFactory().createGovernor();
                ((OrdinaryGovernorAdapter) governor).setWorker((Worker) asynchronousPoolFactory().getAsynchronous());
                return governor;
            }
        }
    }

}
