package org.cheetah.fighter.engine.support;

import org.cheetah.fighter.engine.AbstractEngine;
import org.cheetah.fighter.governor.Governor;
import org.cheetah.fighter.worker.Worker;
import org.cheetah.fighter.governor.support.ForeseeableGovernor;
import org.cheetah.fighter.governor.support.ForeseeableGovernorAdapter;

import java.util.Objects;

/**
 * Created by Max on 2016/2/1.
 */
public class ForeseeableEngine extends AbstractEngine<Worker[]> {

    public ForeseeableEngine() {
        this.state = State.NEW;
    }

    @Override
    public Worker[] getAsynchronous() {
        return (Worker[]) asynchronousPoolFactory().getAsynchronous();
    }
/*
    @Override
    public Governor getAsynchronous() {
        if (Objects.isNull(governor())) {
            Governor governor = governorFactory().createGovernor();
            governor = new ForeseeableGovernorAdapter((ForeseeableGovernor) governor, pluginChain());
            ((ForeseeableGovernorAdapter) governor).setWorkers((Worker[]) asynchronousPoolFactory().getAsynchronous());
            governor.registerHandlerSquad(context().handlers());
            setGovernor(governor);
            return governor;
        } else {
            try {
                Governor clone = governor().kagebunsin();
                clone.reset();
                Worker[] workers = (Worker[]) asynchronousPoolFactory().getAsynchronous();
                ((ForeseeableGovernorAdapter) clone).setWorkers(workers);
                return clone;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                Governor governor = governorFactory().createGovernor();
                ((ForeseeableGovernorAdapter) governor).setWorkers((Worker[]) asynchronousPoolFactory().getAsynchronous());
                return governor;
            }
        }
    }*/

}
