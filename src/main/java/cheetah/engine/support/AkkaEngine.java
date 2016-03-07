package cheetah.engine.support;

import akka.actor.ActorRef;
import cheetah.engine.AbstractEngine;
import cheetah.governor.Governor;
import cheetah.governor.support.AkkaGovernor;
import cheetah.governor.support.AkkaGovernorAdapter;

import java.util.Objects;

/**
 * Created by Max on 2016/2/1.
 */
public class AkkaEngine extends AbstractEngine {

    public AkkaEngine() {
        this.state = State.NEW;
    }

    @Override
    public Governor assignGovernor() {
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

}
