package cheetah.distributor.machinery.actor;

import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.routing.Router;
import cheetah.distributor.event.Event;
import cheetah.distributor.machinery.Instruction;
import cheetah.logger.Warn;
import scala.Option;
import scala.concurrent.duration.Duration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Max on 2016/2/19.
 */
public class ActorWatcher extends UntypedActor {
    private static final Map<Class<? extends Event>, Router> routerMap = new ConcurrentHashMap<>();

    public void allocation (Instruction instruction) {
//        switch (Worker.ProcessMode.formatFrom(task.getMessage().getProcessMode())) {
//            case UNIMPEDED:
//                if (!routerMap.containsKey(task.getMessage().getEvent())) {
//                    List<Routee> routees = new ArrayList<>();
//                    task.getEventListeners().forEach(e -> {
//                        String name = ActorUnit.class.getSimpleName() + "-" + IDGenerator.generateId();
//                        ActorRef worker = getContext().actorOf(Props.create(ActorUnit.class), name);
//                        getContext().watch(worker);
//                        routees.add(new ActorRefRoutee(worker));
//                    });
//                    if (!routees.isEmpty()) {
//                        Router router = new Router(new BroadcastRoutingLogic(), routees);
//                        routerMap.put(task.getMessage().getEvent().getClass(), router);
//                        router.route(task, getSender());
//                        for (ActorRef actorRef : getContext().getChildren()) {
//                            System.out.println(actorRef.path());
//                        }
//                    }
//                } else {
//                    Router router = routerMap.get(task.getMessage().getEvent().getClass());
//                    router.route(task, getSender());
//                }
//        }
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Instruction)
            allocation((Instruction) message);
        else {
            unhandled(message);
        }
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Warn.log(getClass(), "actor =>" + getSelf().path() + "-----> restart");
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        Warn.log(getClass(), "actor =>" + getSelf().path() + "-----> postStop");
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(3, Duration.create(1, SECONDS), param -> {
            if (param instanceof ArithmeticException) {
                return SupervisorStrategy.resume();
            } else if (param instanceof NullPointerException) {
                return SupervisorStrategy.restart();
            } else if (param instanceof IllegalArgumentException) {
                return SupervisorStrategy.stop();
            } else return SupervisorStrategy.escalate();
        });
    }
}
