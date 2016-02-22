package cheetah.distributor.machine;

import cheetah.distributor.event.Event;
import cheetah.logger.Debug;
import cheetah.logger.Info;
import cheetah.logger.Warn;

import java.util.EventListener;

/**
 * 工人-每个lisnter都会配一个Worker负责监控和处理
 * Created by Max on 2016/2/1.
 */
public interface Machine extends Cloneable {

    default Report tell(Event event, boolean needResult) {
        Report report = Report.NULL;
        if (needResult)
            report = completeWork(event);
        else work(event);
        if (!Report.isNull(report) && report.isFail())
            onFailure(event);
        else
            onSuccess(event);
        return report;
    }

    default void onFailure(Event event) {
        Debug.log(this.getClass(), "worker work failure event is [" + event + "]");
        Warn.log(this.getClass(), "worker work failure event is [" + event + "]");
    }

    default void onSuccess(Event event) {
        Info.log(this.getClass(), "worker work success event is [" + event + "]");
    }

    /**
     * @param event
     */
    void work(Event event);

    Report completeWork(Event event);

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
