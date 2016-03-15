package cheetah.fighter.worker.support;

import akka.actor.*;
import cheetah.fighter.async.akka.ActorFactory;
import cheetah.commons.logger.Debug;
import cheetah.fighter.worker.Command;
import scala.Option;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by Max on 2016/3/1.
 */
public class WorkerMaster extends UntypedActor {
    private ActorRef worker;
    {
        worker = getContext().actorOf(Props.create(AkkaWorker.class), "worker");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message.equals(ActorFactory.STATUS_CHECK_MSG)) {
            getSender().tell(ActorFactory.STATUS_OK, getSelf());
        } else if (message instanceof Command) {
            worker.tell(message, getSender());
        } else {
            unhandled(message);
        }
    }

    @Override
    public void preStart() throws Exception {
        Debug.log(getClass(), "worker actor =>" + getSelf().path() + "-----> start");
    }

    @Override
    public void postStop() throws Exception {
        Debug.log(getClass(), "worker actor =>" + getSelf().path() + "-----> postStop");
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Debug.log(getClass(), "worker actor =>" + getSelf().path() + "-----> preRestart");
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        Debug.log(getClass(), "worker actor =>" + getSelf().path() + "-----> postRestart");
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(3, Duration.create(1, TimeUnit.SECONDS), param -> {
            if (param instanceof ArithmeticException) {
                return SupervisorStrategy.resume();
            } else if (param instanceof NullPointerException) {
                return SupervisorStrategy.restart();
            } else if (param instanceof IllegalArgumentException) {
                return SupervisorStrategy.restart();
            } else return SupervisorStrategy.escalate();
        });
    }
}
