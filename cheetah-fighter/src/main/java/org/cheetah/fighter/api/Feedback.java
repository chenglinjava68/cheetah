package org.cheetah.fighter.api;


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
    /**
     * 消费是否成功
     */
    private Boolean success;
    /**
     * key为消费者执行产生的异常，Class对应的是消费者的DomainEventListener的类
     */
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
