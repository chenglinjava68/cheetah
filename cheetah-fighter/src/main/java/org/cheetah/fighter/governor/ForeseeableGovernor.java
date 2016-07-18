package org.cheetah.fighter.governor;

import com.sun.java.accessibility.util.Translator;
import org.cheetah.commons.logger.Debug;
import org.cheetah.fighter.core.Feedback;
import org.cheetah.fighter.core.governor.AbstractGovernor;
import org.cheetah.fighter.core.handler.Handler;
import org.cheetah.fighter.core.worker.Command;
import org.cheetah.fighter.core.worker.Worker;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/29.
 */
public class ForeseeableGovernor extends AbstractGovernor {
    private Worker[] workers;

    @Override
    protected Feedback notifyAllWorker() {
        Debug.log(this.getClass(), "notify {} worker", workers.length);
        if (handlers().isEmpty())
            return Feedback.EMPTY;
        for (int i = 0; i < workers.length; i++) {
            Command command = Command.of(details().event(), true);
            workers[i].work(command);
        }
        return Feedback.SUCCESS;
    }

    public void setWorkers(Worker[] workers) {
        this.workers = workers;
    }
}
