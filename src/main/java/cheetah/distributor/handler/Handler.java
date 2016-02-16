package cheetah.distributor.handler;

import cheetah.distributor.EventMessage;
import cheetah.event.Event;

import java.util.concurrent.CompletableFuture;


/**
 * 事件处理器
 * Created by Max on 2016/2/1.
 */
public interface Handler {
    void handle(EventMessage event, HandleCallback callback);

    void handle(Event event, boolean state);

    CompletableFuture<Boolean> getFuture();

    void removeFuture();
}
