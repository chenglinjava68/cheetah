package cheetah.core;

import java.util.EventListener;
import java.util.List;

/**
 * Created by Max on 2016/1/29.
 */
public class EventResult {
    private Object source;
    private boolean fail;
    private List<Class<? extends EventListener>> exceptionListeners;

    public EventResult(Object source) {
        this.source = source;
    }

    public EventResult(Object source, boolean fail, List<Class<? extends EventListener>> exceptionListeners) {
        this.source = source;
        this.fail = fail;
        this.exceptionListeners = exceptionListeners;
    }

    public EventResult(Object source, boolean fail) {
        this.source = source;
        this.fail = fail;
    }

    public Object source() {
        return source;
    }

    public boolean isFail() {
        return fail;
    }

    public List<Class<? extends EventListener>> exceptionListeners() {
        return exceptionListeners;
    }
}
