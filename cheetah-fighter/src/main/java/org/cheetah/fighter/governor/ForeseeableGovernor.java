package org.cheetah.fighter.governor;

import org.cheetah.commons.logger.Loggers;
import org.cheetah.fighter.core.Feedback;
import org.cheetah.fighter.core.governor.AbstractGovernor;
import org.cheetah.fighter.core.worker.Command;
import org.cheetah.fighter.core.worker.Worker;

/**
 * Created by Max on 2016/2/29.
 */
public class ForeseeableGovernor extends AbstractGovernor {
    private Worker[] workers;
    private int workerSize;

    @Override
    protected Feedback notifyAllWorker() {
        long start = System.currentTimeMillis();
        if (workerSize < 1)
            return Feedback.EMPTY;
        for (int i = 0; i < workerSize; i++) {
            Command command = Command.of(details().event(), true);
            workers[i].work(command);
        }
        Loggers.me().debugEnabled(this.getClass(), "消耗了{}毫秒", System.currentTimeMillis() - start);
        return Feedback.SUCCESS;
    }

    public void setWorkers(Worker[] workers) {
        this.workers = workers;
        this.workerSize = workers.length;
    }
}
