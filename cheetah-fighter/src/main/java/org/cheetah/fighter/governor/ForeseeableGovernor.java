package org.cheetah.fighter.governor;

import com.sun.java.accessibility.util.Translator;
import org.cheetah.commons.logger.Debug;
import org.cheetah.fighter.core.Feedback;
import org.cheetah.fighter.core.governor.AbstractGovernor;
import org.cheetah.fighter.core.worker.Command;
import org.cheetah.fighter.core.worker.Worker;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/29.
 */
public class ForeseeableGovernor extends AbstractGovernor {
    private Worker worker;

    @Override
    protected Feedback notifyAllWorker() {
        Debug.log(this.getClass(), "notify {} worker", handlerMap().size());
        if (handlerMap().isEmpty())
            return Feedback.EMPTY;
        Translator translator = new Translator();
        for (Class<? extends EventListener> clz : this.handlerMap().keySet()) {
            Command command = Command.of(details().event(), clz);
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
