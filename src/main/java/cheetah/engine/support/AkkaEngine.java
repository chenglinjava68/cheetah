package cheetah.engine.support;

import akka.actor.ActorRef;
import cheetah.engine.AbstractEngine;
import cheetah.engine.Engine;
import cheetah.governor.Governor;
import cheetah.governor.support.AkkaGovernor;

import java.util.Objects;

/**
 * Created by Max on 2016/2/1.
 */
public class AkkaEngine extends AbstractEngine {

    public AkkaEngine() {
        this.state = Engine.State.NEW;
    }

    protected void initialize() {
        asynchronousPoolFactory().start();
    }

    @Override
    public Governor assignGovernor() {
        if(Objects.isNull(governor())) {
            Governor governor = governorFactory().createGovernor();
            ((AkkaGovernor) governor).setWorker((ActorRef) asynchronousPoolFactory().getAsynchronous());
            governor.registerMachineSquad(context().getHandlers());
            setGovernor(governor);
            return governor;
        } else {
            try {
                Governor clone = governor().kagebunsin();
                clone.reset();
                ActorRef actor = (ActorRef) asynchronousPoolFactory().getAsynchronous();
                ((AkkaGovernor) clone).setWorker(actor);
                return clone;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                Governor governor = governorFactory().createGovernor();
                ((AkkaGovernor) governor).setWorker((ActorRef) asynchronousPoolFactory().getAsynchronous());
                return governor;
            }
        }
    }

}
