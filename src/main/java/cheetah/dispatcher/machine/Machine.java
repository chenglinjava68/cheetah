package cheetah.dispatcher.machine;

import cheetah.dispatcher.event.Event;
import cheetah.logger.Debug;
import cheetah.logger.Info;

import java.util.EventListener;

/**
 * 工人-每个lisnter都会配一个Worker负责监控和处理
 * Created by Max on 2016/2/1.
 */
public interface Machine extends Cloneable {

    default Feedback send(Event event, boolean needResult) {
        Feedback report = Feedback.NULL;
        if (needResult)
            report = completeWork(event);
        else execute(event);
        if (!Feedback.isNull(report) && report.isFail())
            onFailure(event);
        else
            onSuccess(event);
        return report;
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

    Feedback completeWork(Event event);

    void setEventListener(EventListener eventListener);

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
