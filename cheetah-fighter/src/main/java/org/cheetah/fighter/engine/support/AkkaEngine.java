package org.cheetah.fighter.engine.support;

import akka.actor.ActorRef;
import org.cheetah.fighter.engine.AbstractEngine;
import org.cheetah.fighter.worker.WorkerAdapter;

/**
 * Created by Max on 2016/2/1.
 */
public class AkkaEngine extends AbstractEngine<ActorRef> {

    public AkkaEngine() {
        this.state = State.NEW;
    }

    @Override
    public WorkerAdapter assignWorkerAdapter() {
        return null;
    }

    @Override
    public ActorRef getAsynchronous() {
        return (ActorRef) getAsynchronousPoolFactory().getAsynchronous();
    }

    /*@Override
    public Governor getAsynchronous() {
        if(Objects.isNull(governor())) {
            Governor governor = governorFactory().createGovernor();
            governor = new AkkaGovernorAdapter((AkkaGovernor) governor, getPluginChain());
            ((AkkaGovernorAdapter) governor).setWorker((ActorRef) getAsynchronousPoolFactory().getAsynchronous());
            governor.registerHandlerSquad(getContext().getHandlers());
            setGovernor(governor);
            return governor;
        } else {
            try {
                Governor clone = governor().kagebunsin();
                clone.reset();
                ActorRef actor = (ActorRef) getAsynchronousPoolFactory().getAsynchronous();
                ((AkkaGovernorAdapter) clone).setWorker(actor);
                return clone;
            } catch (CloneNotSupportedException e) {
                Governor governor = governorFactory().createGovernor();
                ((AkkaGovernorAdapter) governor).setWorker((ActorRef) getAsynchronousPoolFactory().getAsynchronous());
                return governor;
            }
        }
    }
*/
}
