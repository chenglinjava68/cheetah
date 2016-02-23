package cheetah.dispatcher.governor.support;

import akka.actor.*;
import cheetah.dispatcher.governor.Command;
import cheetah.dispatcher.worker.Order;
import cheetah.dispatcher.worker.support.AkkaWorker;
import cheetah.logger.Debug;
import cheetah.logger.Warn;
import scala.Option;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by Max on 2016/2/19.
 */
public class WorkerMaster extends UntypedActor {

    public void allocation(Command command) {
        if (command.isNeedReport()) {

        } else {
            ActorRef actor = this.getContext().getChildren().iterator().next();
            System.out.println(System.currentTimeMillis());
            command.getMachines().forEach(machine -> {
//                String name = AkkaWorker.class.getSimpleName() + "-" + IDGenerator.generateId();
//                ActorRef actor = getContext().actorOf(Props.create(AkkaWorker.class), name);
//                getContext().watch(actor);
                Inbox inbox = Inbox.create(getContext().system());
                inbox.watch(actor);
                inbox.send(actor, new Order(machine, command.getEvent(), false));
            });

            System.out.println("master: " + System.currentTimeMillis());
        }
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Command) {
            Command command = (Command) message;
            allocation(command);
        } else {
            unhandled(message);
        }
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        ActorRef actor = getContext().actorOf(Props.create(AkkaWorker.class), AkkaWorker.class.getName());
        getContext().watch(actor);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Warn.log(getClass(), "actor =>" + getSelf().path() + "-----> restart");
        Debug.log(getClass(), "actor =>" + getSelf().path() + "-----> restart");
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        Warn.log(getClass(), "actor =>" + getSelf().path() + "-----> postStop");
        Debug.log(getClass(), "actor =>" + getSelf().path() + "-----> postStop");
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
