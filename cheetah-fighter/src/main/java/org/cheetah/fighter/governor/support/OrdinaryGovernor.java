package org.cheetah.fighter.governor.support;

import org.cheetah.fighter.governor.AbstractGovernor;
import org.cheetah.fighter.handler.Feedback;
import org.cheetah.fighter.worker.Command;
import org.cheetah.fighter.worker.Worker;
import com.sun.java.accessibility.util.Translator;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/29.
 */
public class OrdinaryGovernor extends AbstractGovernor {
    private Worker worker;

    @Override
    protected Feedback notifyAllWorker() {
        if (handlerMap().isEmpty())
            return Feedback.EMPTY;
        Translator translator = new Translator();
        for (Class<? extends EventListener> clz : this.handlerMap().keySet()) {
            Command command = Command.of(details().event(), details().callback(), clz);
            worker.work(command);
        }

//        if (!feedbackMap.isEmpty()) {
//            interceptorChain.pluginAll(feedbackMap);
//        }
        return Feedback.SUCCESS;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
