package org.cheetah.fighter.api;

import java.util.concurrent.TimeUnit;

/**
 * Created by Max on 2016/2/21.
 */
public class EventMessage {
    /**
     * 需要触发的领域事件
     */
    private DomainEvent event;
    /**
     * 是否需要知道结果
     */
    private boolean needResult;
    /**
     * 消费超时时间
     */
    private int timeout;
    /**
     * 消费超时时间单位
     */
    private TimeUnit timeUnit;

    EventMessage(Builder builder) {
        this.event = builder.event;
        this.needResult = builder.needResult;
        this.timeout = builder.timeout;
        this.timeUnit = builder.timeUnit;
    }

    public DomainEvent event() {
        return event;
    }

    public boolean needResult() {
        return needResult;
    }

    public int timeout() {
        return timeout;
    }

    public TimeUnit timeUnit() {
        return timeUnit;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "event=" + event +
                ", needResult=" + needResult +
                ", timeout=" + timeout +
                ", timeUnit=" + timeUnit +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        DomainEvent event;
        boolean needResult = false;
        int timeout = 0;
        TimeUnit timeUnit = TimeUnit.SECONDS;

        public EventMessage build() {
            return new EventMessage(this);
        }

        public Builder event(DomainEvent event) {
            this.event = event;
            return this;
        }

        public Builder needResult(boolean needResult) {
            this.needResult = needResult;
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder timeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }
    }
}
