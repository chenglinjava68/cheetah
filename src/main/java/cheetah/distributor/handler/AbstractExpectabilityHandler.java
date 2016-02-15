package cheetah.distributor.handler;

import java.util.EventListener;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Created by Max on 2016/2/14.
 */
public abstract class AbstractExpectabilityHandler extends BaseHandlerSupply implements ExpectabilityHandler {
    private CompletableFuture<Boolean> future;

    public AbstractExpectabilityHandler(EventListener eventListener, ExecutorService executorService) {
        super(eventListener, executorService);
    }

    @Override
    public final CompletableFuture<Boolean> getFuture() {
        return future;
    }

    protected final void setFuture(CompletableFuture<Boolean> future) {
        this.future = future;
    }

}
