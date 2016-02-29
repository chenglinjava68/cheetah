package cheetah.governor;

import cheetah.event.Event;
import cheetah.machine.Feedback;
import cheetah.machine.Machine;
import cheetah.plugin.InterceptorChain;

import java.util.EventListener;
import java.util.Map;

/**
 * Created by Max on 2016/2/20.
 */
public interface Governor extends Cloneable {
    Governor reset();

    Governor initialize();

    Feedback command();

    Governor setEvent(Event $event);

    Event getEven();

    String getId();

    Governor setFisrtSucceed(Boolean $fisrtSucceed);

    Governor registerMachineSquad(Map<Class<? extends EventListener>, Machine> $workers);

    Governor setNeedResult(boolean $needResult);

    void removeWorker(Machine machine);

    void setInterceptorChain(InterceptorChain $interceptorChain);

    Object kagebunsin() throws CloneNotSupportedException;

}
