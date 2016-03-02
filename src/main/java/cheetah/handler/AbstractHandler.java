package cheetah.handler;

import cheetah.event.Event;
import cheetah.util.ObjectUtils;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/14.
 */
public abstract class AbstractHandler implements Handler {
    private EventListener eventListener;

    public AbstractHandler() {
    }

    public AbstractHandler(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public Handler kagebunsin() throws CloneNotSupportedException {
        return (Handler) super.clone();
    }

    @Override
    public Handler kagebunsin(EventListener listener) throws CloneNotSupportedException {
        Handler handler = (Handler) super.clone();
        handler.setEventListener(this.eventListener);
        return handler;
    }

    @Override
    public void execute(Event event) {
        doExecute(event);
    }

    @Override
    public Feedback completeExecute(Event event) {
        try {
            doExecute(event);
        } catch (Exception e) {
            e.printStackTrace();
            return Feedback.FAILURE;
        }
        return Feedback.SUCCESS;
    }

    protected abstract void doExecute(Event event);

    @Override
    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public EventListener getEventListener() {
        return eventListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractHandler that = (AbstractHandler) o;

        return ObjectUtils.nullSafeEquals(this.eventListener, that.eventListener);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(this.eventListener) * 29;
    }

}
