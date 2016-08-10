package org.cheetah.fighter.worker.support;

import com.google.common.collect.ImmutableList;
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
    private final ImmutableList<Worker> workers;

    public ForeseeableWorkerAdapter(Worker[] workers) {
        this.workers = ImmutableList.copyOf(workers);
    }

    @Override
    public boolean supports(Object object) {
        return object == EngineStrategy.FUTURE;
    }

    @Override
    public Feedback work(EventMessage eventMessage) {
        if (workers.isEmpty())
            return Feedback.EMPTY;
        Feedback[] feedbacks = new Feedback[workers.size()];

        for (int i = 0; i < workers.size(); i++) {
            Command command = Command.of(eventMessage.event(), eventMessage.needResult(), eventMessage.timeout(), eventMessage.timeUnit());
            Feedback feedback = workers.get(i).work(command);
            feedbacks[i] = feedback;
        }

        boolean fail = Arrays.stream(feedbacks).anyMatch(o -> !o.isSuccess());
        if (fail) {
            Map<Exception, Class<?>> result = Arrays.stream(feedbacks).filter(o -> !o.isSuccess()).collect(Collectors.toMap(Feedback::getException, Feedback::getFailureListener));
            return new Feedback(false, result);
        }
        return Feedback.SUCCESS;
    }

}
