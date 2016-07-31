package org.cheetah.fighter.worker.support;

import org.cheetah.fighter.EventMessage;
import org.cheetah.fighter.Feedback;
import org.cheetah.fighter.engine.support.EngineStrategy;
import org.cheetah.fighter.worker.Command;
import org.cheetah.fighter.worker.Worker;
import org.cheetah.fighter.worker.WorkerAdapter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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
        Feedback[] feedbacks = new Feedback[workerSize];

        for (int i = 0; i < workerSize; i++) {
            Command command = Command.of(eventMessage.event(), eventMessage.needResult(), eventMessage.timeout(), eventMessage.timeUnit());
            Feedback feedback = workers[i].work(command);
            feedbacks[i] = feedback;
        }

        boolean fail = Arrays.stream(feedbacks).anyMatch(o -> !o.isSuccess());
        if (fail) {
            Map<Exception, Class<?>> result = Arrays.stream(feedbacks).filter(o -> !o.isSuccess()).collect(Collectors.toMap(Feedback::getException, Feedback::getFailureListener));
            return new Feedback(false, result);
        }
        return Feedback.SUCCESS;
    }

    public void setWorkers(Worker[] workers) {
        this.workers = workers;
        this.workerSize = workers.length;
    }
}
