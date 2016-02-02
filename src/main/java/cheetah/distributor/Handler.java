package cheetah.distributor;

import cheetah.event.Event;

import java.util.concurrent.CompletableFuture;

/**
 * 事件处理器接口
 * Created by Max on 2016/2/1.
 */
public interface Handler {
    Handler handle(Event event);

    CompletableFuture getResult();
}
