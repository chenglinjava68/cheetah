package org.cheetah.fighter.engine.support;

import akka.actor.ActorRef;
import org.cheetah.fighter.engine.AbstractEngine;
import org.cheetah.fighter.governor.Governor;
import org.cheetah.fighter.governor.support.AkkaGovernor;
import org.cheetah.fighter.governor.support.AkkaGovernorAdapter;

import java.util.Objects;

/**
 * Created by Max on 2016/2/1.
 */
public class AkkaEngine extends AbstractEngine<ActorRef> {

    public AkkaEngine() {
        this.state = State.NEW;
    }

    @Override
    public ActorRef getAsynchronous() {
        return (ActorRef) asynchronousPoolFactory().getAsynchronous();
    }

    /*@Override
    public Governor getAsynchronous() {
        if(Objects.isNull(governor())) {
            Governor governor = governorFactory().createGovernor();
            governor = new AkkaGovernorAdapter((AkkaGovernor) governor, pluginChain());
            ((AkkaGovernorAdapter) governor).setWorker((ActorRef) asynchronousPoolFactory().getAsynchronous());
            governor.registerHandlerSquad(context().getHandlers());
            setGovernor(governor);
            return governor;
        } else {
            try {
                Governor clone = governor().kagebunsin();
                clone.reset();
                ActorRef actor = (ActorRef) asynchronousPoolFactory().getAsynchronous();
                ((AkkaGovernorAdapter) clone).setWorker(actor);
                return clone;
            } catch (CloneNotSupportedException e) {
                Governor governor = governorFactory().createGovernor();
                ((AkkaGovernorAdapter) governor).setWorker((ActorRef) asynchronousPoolFactory().getAsynchronous());
                return governor;
            }
        }
    }
*/
}
