package org.cheetah.fighter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Max on 2016/1/29.
 */
public class EventResult {
    private Object source;
    private boolean success;
    private Map<Exception, Class<?>> exceptionClassMap = new HashMap<>();

    public EventResult(Object source, boolean success, Map<Exception, Class<?>> exceptionClassMap) {
        this.source = source;
        this.success = success;
        this.exceptionClassMap = exceptionClassMap;
    }

    public Object getSource() {
        return source;
    }

    public boolean isSuccess() {
        return success;
    }

    public Map<Exception, Class<?>> getExceptionClassMap() {
        return exceptionClassMap;
    }

    @Override
    public String toString() {
        return "EventResult{" +
                "source=" + source +
                ", success=" + success +
                ", exceptionClassMap=" + exceptionClassMap +
                '}';
    }
}
