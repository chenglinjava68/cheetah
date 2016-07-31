package org.cheetah.fighter.worker;

import org.cheetah.fighter.DomainEvent;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Created by Max on 2016/2/22.
 */
public final class Command implements Serializable {

    private static final long serialVersionUID = 2193959876727951577L;

    /**
     * 需要触发的领域事件
     */
    private final DomainEvent event;
    /**
     * 是否需要知道结果
     */
    private final boolean needResult;
    /**
     * 消费超时时间
     */
    private final int timeout;
    /**
     * 消费超时时间单位
     */
    private final TimeUnit timeUnit;

    Command(DomainEvent event, boolean needResult, int timeout, TimeUnit timeUnit) {
        this.event = event;
        this.needResult = needResult;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    public final boolean timeLimit() {
        return timeout() > 0;
    }

    public final DomainEvent event() {
        return event;
    }

    public final boolean needResult() {
        return needResult;
    }

    public final int timeout() {
        return timeout;
    }

    public final TimeUnit timeUnit() {
        return timeUnit;
    }

    public static Command of(DomainEvent event, boolean needResult) {
        return new Command(event, needResult, 0, TimeUnit.SECONDS);
    }

    public static Command of(DomainEvent event, boolean needResult, int timeout, TimeUnit timeUnit) {
        return new Command(event, needResult, timeout, timeUnit);
    }
}
