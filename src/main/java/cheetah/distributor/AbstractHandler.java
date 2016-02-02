package cheetah.distributor;

        import java.util.EventListener;
        import java.util.concurrent.CompletableFuture;
        import java.util.concurrent.ExecutorService;

/**
 * Created by Max on 2016/2/1.
 */
public abstract class AbstractHandler implements Handler {
    private EventListener eventListener;
    private ExecutorService executorService;
    private CompletableFuture future;

    public AbstractHandler(EventListener eventListener, ExecutorService executorService) {
        this.eventListener = eventListener;
        this.executorService = executorService;
    }

    @Override
    public final CompletableFuture getResult() {
        return future;
    }

    public final EventListener getEventListener() {
        return eventListener;
    }

    public final ExecutorService getExecutorService() {
        return executorService;
    }

    protected final void setFuture(CompletableFuture future) {
        this.future = future;
    }
}
