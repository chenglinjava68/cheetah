package org.cheetah.fighter.worker.support;

import com.lmax.disruptor.EventHandler;
import org.cheetah.commons.logger.Debug;
import org.cheetah.commons.logger.Loggers;
import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.DomainEventListener;
import org.cheetah.fighter.async.disruptor.DisruptorEvent;
import org.cheetah.fighter.Interceptor;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.worker.AbstractWorker;
import org.cheetah.fighter.worker.Command;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorWorker extends AbstractWorker implements EventHandler<DisruptorEvent> {

    public DisruptorWorker(DomainEventListener<DomainEvent> eventListener, List<Interceptor> interceptors) {
        super(eventListener, interceptors);
    }

    @Override
    public void onEvent(DisruptorEvent disruptorEvent, long sequence, boolean endOfBatch) throws Exception {
        Command command = disruptorEvent.get();
        work(command);
    }

    @Override
    public void work(Command command) {
        try {
            invoke(command);
            eventListener.onFinish(command.event());
        } catch (Exception e) {
            e.printStackTrace();
            eventListener.onCancelled(command.event(), e);
        }
    }

    @Override
    protected boolean doWork(Command command) {
        eventListener.onDomainEvent(command.event());
        return true;
    }

    @Override
    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

}
