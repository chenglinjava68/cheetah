package cheetah.governor.support;

import cheetah.event.Event;
import cheetah.governor.Governor;
import cheetah.machine.Feedback;
import cheetah.machine.Machine;
import cheetah.plugin.InterceptorChain;

import java.util.EventListener;
import java.util.Map;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorGovernor implements Governor {
    @Override
    public Governor reset() {
        return null;
    }

    @Override
    public Governor initialize() {
        return null;
    }

    @Override
    public Feedback command() {
        return null;
    }

    @Override
    public Governor setEvent(Event $event) {
        return null;
    }

    @Override
    public Event getEven() {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public Governor setFisrtSucceed(Boolean $fisrtSucceed) {
        return null;
    }

    @Override
    public Governor registerMachineSquad(Map<Class<? extends EventListener>, Machine> $workers) {
        return null;
    }

    @Override
    public Governor setNeedResult(boolean $needResult) {
        return null;
    }

    @Override
    public void expelMachine(Machine machine) {

    }

    @Override
    public void setInterceptorChain(InterceptorChain $interceptorChain) {

    }

    @Override
    public Object kagebunsin() throws CloneNotSupportedException {
        return null;
    }
}
