package org.cheetah.fighter.governor.support;

import org.cheetah.fighter.Feedback;
import org.cheetah.fighter.governor.AbstractGovernor;
import org.cheetah.fighter.worker.Command;
import org.cheetah.fighter.worker.Worker;

/**
 * Created by Max on 2016/2/29.
 */
public class ForeseeableGovernor extends AbstractGovernor {
    private Worker[] workers;
    private int workerSize;

    @Override
    protected Feedback notifyAllWorker() {
        if (workerSize < 1)
            return Feedback.EMPTY;
        for (int i = 0; i < workerSize; i++) {
            Command command = Command.of(details().event(), true);
            workers[i].work(command);
        }
        return Feedback.SUCCESS;
    }

    public void setWorkers(Worker[] workers) {
        this.workers = workers;
        this.workerSize = workers.length;
    }
}
