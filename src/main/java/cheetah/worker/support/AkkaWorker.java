package cheetah.worker.support;

import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import cheetah.async.akka.ActorFactory;
import cheetah.common.logger.Debug;
import cheetah.machine.Directive;
import cheetah.machine.Feedback;
import cheetah.machine.Machine;
import cheetah.util.Assert;
import cheetah.worker.Command;
import cheetah.worker.Worker;
import scala.Option;
import scala.concurrent.duration.Duration;

import java.util.EventListener;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaWorker extends UntypedActor implements Worker {
    private Map<Class<? extends EventListener>, Machine> eventlistenerMapper;

    public AkkaWorker(Map<Class<? extends EventListener>, Machine> eventlistenerMapper) {
        this.eventlistenerMapper = eventlistenerMapper;
    }

    @Override
    public void work(Command command) {
        try {
            Assert.notNull(command, "order must not be null");
            Machine machine = eventlistenerMapper.get(command.eventListener());
            Feedback feedback = machine.send(new Directive(command.event(), command.needResult()));
            getSender().tell(feedback, getSelf());
        } catch (Exception e) {
            Debug.log(this.getClass(), "machine execute fail.", e);
            getSender().tell(Feedback.FAILURE, getSelf());
        }
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
