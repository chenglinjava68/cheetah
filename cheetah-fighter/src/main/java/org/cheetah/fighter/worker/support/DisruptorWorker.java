package org.cheetah.fighter.worker.support;

import com.lmax.disruptor.EventHandler;
import org.cheetah.commons.logger.Debug;
import org.cheetah.commons.logger.Err;
import org.cheetah.fighter.Feedback;
import org.cheetah.fighter.Interceptor;
import org.cheetah.fighter.async.disruptor.DisruptorEvent;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.worker.AbstractWorker;
import org.cheetah.fighter.worker.Command;

import java.util.List;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorWorker extends AbstractWorker implements EventHandler<DisruptorEvent> {

    public DisruptorWorker(Handler handler, List<Interceptor> interceptors) {
        super(handler, interceptors);
    }

    @Override
    public void onEvent(DisruptorEvent disruptorEvent, long sequence, boolean endOfBatch) throws Exception {
        Command command = disruptorEvent.get();
        work(command);
    }

    @Override
    public Feedback work(Command command) {
        long start = System.nanoTime();
        try {
            invoke(command);
            handler.onSuccess(command);
        } catch (Exception e) {
            Err.log(this.getClass(), "event handler handle error", e);
            handler.onFailure(command, e);
        }
        if (Debug.isEnabled(this.getClass())) {
            Debug.log(this.getClass(), handler.getEventListener().getClass().getName() + " execution time : {}", (System.nanoTime() - start) + " ns");
        }
        return Feedback.SUCCESS;
    }

    @Override
    protected boolean doWork(Command command) {
        return handler.handle(command);
    }

    @Override
    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

}
