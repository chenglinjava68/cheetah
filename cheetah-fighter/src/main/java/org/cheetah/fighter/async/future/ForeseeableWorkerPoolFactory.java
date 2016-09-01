package org.cheetah.fighter.async.future;

import org.cheetah.commons.logger.Info;
import org.cheetah.fighter.api.EventBus;
import org.cheetah.fighter.api.EventContext;
import org.cheetah.fighter.api.NoMapperException;
import org.cheetah.fighter.async.AsynchronousFactory;
import org.cheetah.fighter.async.AsynchronousPoolFactory;
import org.cheetah.fighter.worker.support.ForeseeableWorker;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Max on 2016/2/29.
 */
public class ForeseeableWorkerPoolFactory implements AsynchronousPoolFactory<ForeseeableWorker[]> {
    private AsynchronousFactory<ForeseeableWorker[]> asynchronousFactory;
    private final Map<EventBus.HandlerMapperKey, ForeseeableWorker[]> workerPool;
    protected EventContext context;

    public ForeseeableWorkerPoolFactory() {
        this.workerPool = new ConcurrentHashMap<>();
    }

    public ForeseeableWorker[] createWorkerTeam() {
        ForeseeableWorker[] workers = this.workerPool.get(EventBus.HandlerMapperKey.generate(context.getEventMessage().event()));
        if(Objects.nonNull(workers))
            return workers;
        return  this.asynchronousFactory.createAsynchronous(context.getEventMessage().event().getClass().getName(),
                context.getHandlers(), context.getInterceptors());
    }

    @Override
    public ForeseeableWorker[] getAsynchronous() {
        ForeseeableWorker[] workers = this.workerPool.get(EventBus.HandlerMapperKey.generate(context.getEventMessage().event()));
        Info.log(this.getClass(), "get asynchronous from pool, {}", workers);
        if (Objects.nonNull(workers)) {
            return workers;
        } else {
            synchronized (this) {
                if(context.getHandlers().isEmpty())
                    throw new NoMapperException();
                workers = createWorkerTeam();
                EventBus.HandlerMapperKey key = EventBus.HandlerMapperKey.generate(context.getEventMessage().event());
                this.workerPool.putIfAbsent(key, workers);
                return this.workerPool.get(key);
            }
        }
    }

    @Override
    public void setEventContext(EventContext context) {
        this.context = context;
    }

    @Override
    public void setAsynchronousFactory(AsynchronousFactory asynchronousFactory) {
        this.asynchronousFactory = asynchronousFactory;
    }

    @Override
    public void start() {
        this.asynchronousFactory.start();
    }

    @Override
    public void stop() {
        this.workerPool.clear();
        this.asynchronousFactory.stop();
    }

}
