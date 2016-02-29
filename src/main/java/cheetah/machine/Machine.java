package cheetah.machine;

import cheetah.event.Event;
import cheetah.common.logger.Debug;
import cheetah.common.logger.Info;

import java.util.EventListener;

/**
 * 每个lisnter都会配一Machine负责监控和处理
 * Created by Max on 2016/2/1.
 */
public interface Machine extends Cloneable {

    default Feedback send(Event event, boolean needResult) {
        Feedback feedback = Feedback.FAILURE;
        if (needResult) {
            feedback = completeExecute(event);
            if (feedback.isFail())
                onFailure(event);
            else
                onSuccess(event);
        } else execute(event);

        return feedback;
    }

    default void onFailure(Event event) {
        Debug.log(this.getClass(), "Machine execute failure event is [" + event + "]");
    }

    default void onSuccess(Event event) {
        Info.log(this.getClass(), "Machine execute success event is [" + event + "]");
    }

    /**
     * @param event
     */
    void execute(Event event);

    Feedback completeExecute(Event event);

    void setEventListener(EventListener eventListener);

    EventListener getEventListener();

    Machine kagebunsin() throws CloneNotSupportedException;

    Machine kagebunsin(EventListener listener) throws CloneNotSupportedException;

    /**
     * handler处理类型
     * UNIMPEDED：无状态无锁
     * STATE：有状态
     */
    enum ProcessMode {
        UNIMPEDED(0), STATE(1);

        private Integer code;

        ProcessMode(Integer code) {
            this.code = code;
        }

        public static ProcessMode formatFrom(Integer code) {
            for (ProcessMode mode : ProcessMode.values()) {
                if (mode.code == code)
                    return mode;
            }
            return UNIMPEDED;
        }
    }
}
