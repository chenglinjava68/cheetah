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
    private Handler handler;
    private final List<Interceptor> interceptors;
    private AtomicLong atomicLong = new AtomicLong();

    public DisruptorWorker(Handler handler, List<Interceptor> interceptors) {
        try {
            this.handler = handler.kagebunsin();
        } catch (CloneNotSupportedException e) {

        }
        this.interceptors = interceptors;
    }

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
        try {
            if(success)
                handler.kagebunsin().onSuccess(command);
            else handler.kagebunsin().onFailure(command, null);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean doWork(Command command) {
        try {
            return handler.kagebunsin().handle(command);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

}
