package org.cheetah.fighter;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 机器的反馈
 * Created by Max on 2016/2/21.
 */
public class Feedback {
    public static final Feedback EMPTY = new Feedback((Boolean) null);
    public static final Feedback FAILURE = new Feedback(false);
    public static final Feedback SUCCESS = new Feedback(true);
    private Boolean success;
    private Map<Exception, Class<?>> exceptionMap = new HashMap<>();

    private Feedback() {
    }

    private Feedback(Boolean success) {
        this.success = success;
    }

    public Feedback(Exception exception, Class<?> failureListener) {
        this.exceptionMap.put(exception, failureListener);
        this.success = false;
    }

    public Feedback(Boolean success, Map<Exception, Class<?>> exceptionClassMap) {
        this.success = success;
        this.exceptionMap = exceptionClassMap;
    }

    public static Feedback success() {
        return new Feedback(Boolean.TRUE);
    }

    public static Feedback failure(Exception exception, Class<?> listenerClass) {
        return new Feedback(exception, listenerClass);
    }

    public boolean isSuccess() {
        return success;
    }

    public Map<Exception, Class<?>> getExceptionMap() {
        return exceptionMap;
    }

    public Set<Exception> getExceptions() {
        return exceptionMap.keySet();
    }

    public Exception getException() {
        if (exceptionMap.keySet().isEmpty())
            return null;
        return exceptionMap.keySet().stream().findFirst().get();
    }

    public Set<Class<?>> getFailureListeners() {
        return new HashSet<>(exceptionMap.values());
    }

    public Class<?> getFailureListener() {
        if (exceptionMap.values().isEmpty())
            return null;
        return exceptionMap.values().stream().findFirst().get();
    }
}
