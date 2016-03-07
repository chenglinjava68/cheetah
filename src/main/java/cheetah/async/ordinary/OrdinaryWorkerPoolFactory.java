package cheetah.async.ordinary;

import cheetah.async.AsynchronousFactory;
import cheetah.async.AsynchronousPoolFactory;
import cheetah.core.NoMapperException;
import cheetah.core.EventContext;
import cheetah.mapping.HandlerMapping;
import cheetah.worker.support.OrdinaryWorker;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Max on 2016/2/29.
 */
public class OrdinaryWorkerPoolFactory implements AsynchronousPoolFactory<OrdinaryWorker> {
    private AsynchronousFactory<OrdinaryWorker> asynchronousFactory;
    private final Map<HandlerMapping.HandlerMapperKey, OrdinaryWorker> workerPool;
    private EventContext context;

    public OrdinaryWorkerPoolFactory() {
        this.workerPool = new HashMap<>();
    }

    public OrdinaryWorker createWorker() {
        OrdinaryWorker worker = this.workerPool.get(HandlerMapping.HandlerMapperKey.generate(context.getEventMessage().event()));
        if(Objects.nonNull(worker))
            return worker;
        return  this.asynchronousFactory.createAsynchronous(context.getEventMessage().event().getClass().getName(), context.getHandlers());
    }

    @Override
    public OrdinaryWorker getAsynchronous() {
        OrdinaryWorker worker = this.workerPool.get(HandlerMapping.HandlerMapperKey.generate(context.getEventMessage().event()));
        if (Objects.nonNull(worker)) {
            return worker;
        } else {
            synchronized (this) {
                if(context.getHandlers().isEmpty())
                    throw new NoMapperException();
                worker = createWorker();
                HandlerMapping.HandlerMapperKey key = HandlerMapping.HandlerMapperKey.generate(context.getEventMessage().event());
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
