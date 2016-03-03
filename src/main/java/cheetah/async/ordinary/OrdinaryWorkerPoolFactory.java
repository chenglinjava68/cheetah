package cheetah.async.ordinary;

import cheetah.async.AsynchronousFactory;
import cheetah.async.AsynchronousPoolFactory;
import cheetah.core.NoMapperException;
import cheetah.handler.EventContext;
import cheetah.mapper.Mapper;
import cheetah.worker.support.OrdinaryWorker;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Max on 2016/2/29.
 */
public class OrdinaryWorkerPoolFactory implements AsynchronousPoolFactory<OrdinaryWorker> {
    private AsynchronousFactory<OrdinaryWorker> asynchronousFactory;
    private final Map<Mapper.HandlerMapperKey, OrdinaryWorker> workerPool;
    private EventContext context;

    public OrdinaryWorkerPoolFactory() {
        this.workerPool = new ConcurrentHashMap<>();
    }

    public OrdinaryWorker createWorker() {
        OrdinaryWorker worker = this.workerPool.get(Mapper.HandlerMapperKey.generate(context.getEventMessage().event()));
        if(Objects.nonNull(worker))
            return worker;
        return  this.asynchronousFactory.createAsynchronous(context.getEventMessage().event().getClass().getName(), context.getHandlers());
    }

    @Override
    public OrdinaryWorker getAsynchronous() {
        OrdinaryWorker worker = this.workerPool.get(Mapper.HandlerMapperKey.generate(context.getEventMessage().event()));
        if (Objects.nonNull(worker)) {
            return worker;
        } else {
            synchronized (this) {
                if(context.getHandlers().isEmpty())
                    throw new NoMapperException();
                worker = createWorker();
                Mapper.HandlerMapperKey key = Mapper.HandlerMapperKey.generate(context.getEventMessage().event());
                this.workerPool.putIfAbsent(key, worker);
                return worker;
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

    }

    @Override
    public void stop() {
        this.workerPool.clear();
        this.asynchronousFactory.stop();
    }

}
