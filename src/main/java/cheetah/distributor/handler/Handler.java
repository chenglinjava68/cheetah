package cheetah.distributor.handler;

import java.util.concurrent.CompletableFuture;


/**
 * 事件处理器
 * Created by Max on 2016/2/1.
 */
public interface Handler {
    /**
     *
     * @param eventMessage
     */
    void handle(EventMessage eventMessage);

    /**
     *
     * @param eventMessage
     * @param nativeAsync
     */
    void handle(EventMessage eventMessage, boolean nativeAsync);

    /**
     *
     * @param eventMessage
     * @param callback
     */
    void handle(EventMessage eventMessage, HandleCallback callback);

    /**
     *
     * @return
     */
    CompletableFuture<Boolean> getFuture();

    /**
     *
     */
    void removeFuture();

    /**
     * handler处理类型
     * UNIMPEDED：无状态无锁
     * JDK_UNIMPEDED：原生jdk并发线程池
     * STATE：有状态需要手动处理
     * STATE_CALL_BACK：有状态通过回调函数做出处理
     */
    enum ProcessMode {
        UNIMPEDED(0), @Deprecated JDK_UNIMPEDED(1), STATE(2), STATE_CALL_BACK(3);

        private Integer code;

        ProcessMode(Integer code) {
            this.code = code;
        }

        public static ProcessMode formatFrom(Integer code) {
            for(ProcessMode mode : ProcessMode.values()) {
                if(mode.code == code)
                    return mode;
            }
            return UNIMPEDED;
        }
    }
}
