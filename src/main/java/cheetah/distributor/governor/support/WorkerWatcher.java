package cheetah.distributor.governor.support;

import akka.actor.*;
import cheetah.distributor.governor.Command;
import cheetah.distributor.worker.support.WorkUnit;
import cheetah.distributor.machine.Order;
import cheetah.logger.Debug;
import cheetah.logger.Warn;
import cheetah.util.IDGenerator;
import scala.Option;
import scala.concurrent.duration.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Max on 2016/2/19.
 */
public class WorkerWatcher extends UntypedActor {

    public void allocation(Command command) {
        if (command.isNeedReport()) {

        } else {
            ActorRef actor = this.getContext().getChildren().iterator().next();
            System.out.println(command.getWorkers());
            command.getWorkers().forEach(worker -> {
                String name = WorkUnit.class.getSimpleName() + "-" + IDGenerator.generateId();
                actor.tell(new Order(name, worker, command.getEvent(), false), getSender());
            });

//            String name = WorkUnit.class.getSimpleName() + "-" + IDGenerator.generateId();
//            Iterator<ActorRef> childs = getContext().getChildren().iterator();
//            ActorRef actorRef;
//            if (!childs.hasNext()) {
//                actorRef = createWorkUnit(name);
//                for (Worker worker : command.getWorkers()) {
//                    actorRef.tell(new Order(name, worker, command.getEvent(), false), getSender());
//                }
//            } else {
//                actorRef = childs.next();
//                if (actorRef.isTerminated()) {
//                    actorRef = createWorkUnit(name);
//                    for (Worker worker : command.getWorkers()) {
//                        actorRef.tell(new Order(name, worker, command.getEvent(), false), getSender());
//                    }
//                } else
//                    for (Worker worker : command.getWorkers()) {
//                        actorRef.tell(new Order(name, worker, command.getEvent(), false), getSender());
//                    }
//            }
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
        ActorRef actor = getContext().actorOf(Props.create(WorkUnit.class), WorkUnit.class.getName());
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
        return new OneForOneStrategy(3, Duration.create(1, SECONDS), param -> {
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
