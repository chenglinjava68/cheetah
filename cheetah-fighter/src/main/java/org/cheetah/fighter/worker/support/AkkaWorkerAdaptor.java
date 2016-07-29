package org.cheetah.fighter.worker.support;

import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.japi.Function;
import org.cheetah.common.logger.Info;
import org.cheetah.fighter.async.akka.ActorFactory;
import org.cheetah.fighter.Interceptor;
import org.cheetah.fighter.worker.Command;
import org.cheetah.fighter.worker.Worker;
import scala.Option;
import scala.concurrent.duration.Duration;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Max on 2016/5/4.
 */
public class AkkaWorkerAdaptor extends UntypedActor implements Worker {
    private Worker worker;

    public AkkaWorkerAdaptor(Worker worker) {
        this.worker = worker;
    }

    @Override
    public void work(Command command) {
        worker.work(command);
    }

    @Override
    public List<Interceptor> getInterceptors() {
        return worker.getInterceptors();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message.equals(ActorFactory.STATUS_CHECK_MSG)) {
            getSender().tell(ActorFactory.STATUS_OK, getSelf());
        } else if (message instanceof Command) {
            work((Command) message);
        } else unhandled(message);
    }


    @Override
    public void preStart() throws Exception {
        Info.log(getClass(), "worker actor =>" + getSelf().path() + "-----> start");
    }

    @Override
    public void postStop() throws Exception {
        Info.log(getClass(), "worker actor =>" + getSelf().path() + "-----> postStop");
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Info.log(getClass(), "worker actor =>" + getSelf().path() + "-----> preRestart");
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        Info.log(getClass(), "worker actor =>" + getSelf().path() + "-----> postRestart");
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(3, Duration.create(1, TimeUnit.SECONDS), new Function<Throwable, SupervisorStrategy.Directive>() {
            @Override
            public SupervisorStrategy.Directive apply(Throwable throwable) throws Exception {
                if (throwable instanceof ArithmeticException) {
                    return SupervisorStrategy.resume();
                } else if (throwable instanceof NullPointerException) {
                    return SupervisorStrategy.restart();
                } else if (throwable instanceof IllegalArgumentException) {
                    return SupervisorStrategy.restart();
                } else return SupervisorStrategy.escalate();
            }
        });
    }
}
