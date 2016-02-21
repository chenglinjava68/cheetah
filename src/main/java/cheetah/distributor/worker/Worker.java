package cheetah.distributor.worker;

import cheetah.distributor.machinery.Machinery;
import cheetah.logger.Debug;
import cheetah.logger.Info;
import cheetah.logger.Warn;

import java.util.EventListener;

/**
 * 工人-每个lisnter都会配一个Worker负责监控和处理
 * Created by Max on 2016/2/1.
 */
public interface Worker extends Cloneable {

    default void tell(Order order) {
        Report report = work(order);
        if (report.isFail())
            onFailure(order);
        else
            onSuccess(order);
    }

    default void onFailure(Order order) {
        Debug.log(this.getClass(), "worker work failure order id is[" + order.getId() + "]");
        Warn.log(this.getClass(), "worker work failure order id is[" + order.getId() + "]");
    }

    default void onSuccess(Order order) {
        Info.log(this.getClass(), "worker work success order id is[" + order.getId() + "]");
    }

    /**
     * @param eventMessage
     */
    Report work(Order eventMessage);

    void setEventListener(EventListener eventListener);

    void setMachinery(Machinery machinery);

    Machinery getMachinery();

    Worker kagebunsin() throws CloneNotSupportedException;

    Worker kagebunsin(EventListener listener) throws CloneNotSupportedException;

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
