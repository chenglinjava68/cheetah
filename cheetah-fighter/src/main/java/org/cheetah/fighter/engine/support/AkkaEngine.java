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

}
