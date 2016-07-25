package org.cheetah.fighter.worker.support;

import com.lmax.disruptor.EventHandler;
import org.cheetah.commons.logger.Debug;
import org.cheetah.commons.logger.Loggers;
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
    private AtomicLong atomicLong = new AtomicLong();

    @Override
    public void onEvent(DisruptorEvent disruptorEvent, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(atomicLong.incrementAndGet());
        Command command = disruptorEvent.get();
        long start = System.currentTimeMillis();
        work(command);
        Loggers.me().debugEnabled(this.getClass(), "work消耗了{}毫秒", System.currentTimeMillis() - start);
    }

    @Override
    public void work(Command command) {
        boolean success = invoke(command);
        if(success)
            handler.onSuccess(command);
        else handler.onFailure(command, null);
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
