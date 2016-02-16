package cheetah.distributor.handler;

import cheetah.distributor.EventMessage;

import java.util.concurrent.CompletableFuture;


/**
 * 事件处理器接口
 * Created by Max on 2016/2/1.
 */
public interface Handler {

    void handle(EventMessage event);

    void handle(EventMessage event, boolean nativeAsync);

    void handle(EventMessage event, HandleExceptionCallback callback);

    CompletableFuture<Boolean> getFuture();

    void removeFuture();

    enum ProcessMode {
         NOSTATE(0), STATE(1);

        private Integer code;

        ProcessMode(Integer code) {
            this.code = code;
        }

        public ProcessMode formatFrom(Integer code) {
            for(ProcessMode mode : ProcessMode.values()) {
                if(mode.code == code)
                    return mode;
            }
            return NOSTATE;
        }
    }
}
