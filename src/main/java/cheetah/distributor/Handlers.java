package cheetah.distributor;

import cheetah.event.Event;
import cheetah.logger.Debug;
import cheetah.plugin.InterceptorChain;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Created by Max on 2016/2/1.
 */
public final class Handlers {
    private final Handler handler;
    private final InterceptorChain interceptorChain;

    public Handlers(Handler handler, InterceptorChain interceptorChain) {
        this.handler = handler;
        this.interceptorChain = interceptorChain;
    }

    public final Object handle(Event<?> event) {
        Objects.requireNonNull(event, "event must be null");
        try {
            return handler.handle(event).getResult().get();
        } catch (InterruptedException e) {
            Debug.log(this.getClass(), "");
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            interceptorChain.pluginAll(event);
        }
        return null;
//            switch (HandlerTyped.Manager.convertFrom(listener.getClass())) {
//                case APP:
//                case GENERIC:
//                case DOMAIN:
//                    new GenericEventHandler(listener, executorService).handle(event).getResult();
//                    break;
//                case SMART_APP:
//                    new ApplicationEventHandler(listener, executorService).handle(event).getResult();
//                    break;
//                case SMART_DOMAIN:
//                    new DomainEventHandler(listener, executorService).handle(event).getResult();
//                    break;
//                default:
//                    throw new EventHandlerException("[cheetah-distributor-handler]: " + "listener type of error.");
//            }
//            CompletableFuture future = new GenericEventHandler(listener, executorService)
//                    .handle(event)
//                    .getResult();
//            try {
//                future.get(1000, TimeUnit.MILLISECONDS);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (TimeoutException e) {
//                e.printStackTrace();
//            }
//        });
    }
}
