package cheetah.distributor.handler;

import java.util.EventListener;
import java.util.concurrent.ExecutorService;

/**
 * Created by Max on 2016/2/14.
 */
public abstract class AbstractUnpredictableHandler extends AbstractHandler {
    public AbstractUnpredictableHandler(EventListener eventListener, ExecutorService executorService) {
        super(eventListener, executorService);
    }
}
