package org.cheetah.fighter.worker.support;

import org.cheetah.fighter.EventMessage;
import org.cheetah.fighter.Feedback;
import org.cheetah.fighter.engine.support.EngineStrategy;
import org.cheetah.fighter.worker.Command;
import org.cheetah.fighter.worker.Worker;
import org.cheetah.fighter.worker.WorkerAdapter;

/**
 * Created by Max on 2016/7/21.
 */
public class ForeseeableWorkerAdapter implements WorkerAdapter {
    private Worker[] workers;
    private int workerSize;

    @Override
    public boolean supports(Object object) {
        return object == EngineStrategy.FUTURE;
    }

    @Override
    public Feedback work(EventMessage eventMessage) {
        if (workerSize < 1)
            return Feedback.EMPTY;
        for (int i = 0; i < workerSize; i++) {
            Command command = Command.of(eventMessage.event(), true);
            workers[i].work(command);
        }
        return Feedback.SUCCESS;
    }

    public void setWorkers(Worker[] workers) {
        this.workers = workers;
        this.workerSize = workers.length;
    }
}
