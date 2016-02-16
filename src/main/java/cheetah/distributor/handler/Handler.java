package cheetah.distributor.handler;

import cheetah.distributor.EventMessage;
import cheetah.event.Event;

import java.util.concurrent.CompletableFuture;


/**
 * 事件处理器接口
 * Created by Max on 2016/2/1.
 */
public interface Handler {

    void handle(Event event);

    void handle(Event event, boolean nativeAsync);

    void handle(EventMessage event, HandleExceptionCallback callback);

    CompletableFuture<Boolean> getFuture();

    void removeFuture();
}
