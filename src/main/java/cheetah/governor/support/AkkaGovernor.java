package cheetah.governor.support;

import akka.actor.ActorRef;
import akka.pattern.Patterns;
import cheetah.event.Event;
import cheetah.governor.Governor;
import cheetah.machine.Feedback;
import cheetah.machine.Machine;
import cheetah.plugin.InterceptorChain;
import cheetah.util.Assert;
import cheetah.util.IDGenerator;
import cheetah.worker.Command;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Max on 2016/2/20.
 */
public class AkkaGovernor implements Governor {
    private String id;
    private Boolean fisrtSucceed;
    private Boolean needResult;
    private Event event;
    private Map<Class<? extends EventListener>, Machine> machines;
    private ActorRef worker;
    private InterceptorChain interceptorChain;

    @Override
    public Governor reset() {
        this.machines = null;
        this.worker = null;
        this.fisrtSucceed = false;
        this.needResult = false;
        this.event = null;
        return this;
    }

    @Override
    public Governor initialize() {
        this.id = IDGenerator.generateId();
        this.fisrtSucceed = false;
        this.needResult = false;
        return this;
    }

    @Override
    public Feedback command() {
        Assert.notNull(event, "event must not be null");
        return notifyAllWorker();
    }

    @Override
    public Governor setEvent(Event $event) {
        Assert.notNull($event, "$event must not be null");
        this.event = $event;
        return this;
    }

    @Override
    public Event getEven() {
        return this.event;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Governor setFisrtSucceed(Boolean $fisrtSucceed) {
        this.fisrtSucceed = $fisrtSucceed;
        return this;
    }

    @Override
    public Governor registerMachineSquad(Map<Class<? extends EventListener>, Machine> machineMap) {
        this.machines = machineMap;
        return this;
    }

    @Override
    public Governor setNeedResult(boolean $needResult) {
        this.needResult = $needResult;
        return this;
    }

    @Override
    public void expelMachine(Machine $worker) {
        Assert.notNull($worker, "$observer must not be null");
        machines.remove($worker.getEventListener().getClass());
    }

    @Override
    public void setInterceptorChain(InterceptorChain $interceptorChain) {
        this.interceptorChain = $interceptorChain;
    }

    @Override
    public Governor kagebunsin() throws CloneNotSupportedException {
        return (Governor) super.clone();
    }

    public void setWorker(ActorRef worker) {
        this.worker = worker;
    }

    private Feedback notifyAllWorker() {
        if (machines.isEmpty())
            return Feedback.EMPTY;
        Map<Class<? extends EventListener>, Feedback> feedbackMap = new HashMap<>();
        System.out.println(System.currentTimeMillis());
        for (Class<? extends EventListener> clz : this.machines.keySet()) {
            try {
                Command command = Command.of(event, clz);
                Future<Object> future = Patterns.ask(this.worker, command, 3000);
                Object result = Await.result(future, Duration.create(3000, TimeUnit.MILLISECONDS));
                if (result instanceof Feedback) {
                    Feedback feedback = (Feedback) result;
                    feedbackMap.put(clz, feedback);
                }
            } catch (Exception e) {
                e.printStackTrace();
                feedbackMap.put(clz, Feedback.FAILURE);
            }
        }

//        if (!feedbackMap.isEmpty()) {
//            interceptorChain.pluginAll(feedbackMap);
//        }
        return Feedback.SUCCESS;
    }
}
