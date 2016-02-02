package cheetah.distributor;

import cheetah.event.Event;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Created by Max on 2016/2/1.
 */
public final class Handlers {
    private final Handler handler;

    public Handlers(Handler handler) {
        this.handler = handler;
    }

    public final Object handle(Event<?> event) throws ExecutionException, InterruptedException {
        Objects.requireNonNull(event, "event must be null");
        return handler.handle(event).getResult().get();

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
