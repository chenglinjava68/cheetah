package cheetah.dispatcher.machine;


import cheetah.util.ObjectUtils;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/21.
 */
public class Feedback {
    public static final Feedback EMPTY = new Feedback(); //没有监听器
    public static final Feedback NULL = new Feedback(); //不需要结果
    public static final Feedback SUCCESS = new Feedback();
    public static final Feedback FAILURE = new Feedback();
    private boolean fail;
    private Class<? extends EventListener> exceptionListener;

    public Feedback() {
    }

    public Feedback(boolean fail, Class<? extends EventListener> exceptionListener) {
        this.fail = fail;
        this.exceptionListener = exceptionListener;
    }

    public boolean isFail() {
        return fail;
    }

    public Class<? extends EventListener> getExceptionListener() {
        return exceptionListener;
    }

    public static Boolean isEmpty(Feedback report) {
        return report.equals(EMPTY);
    }

    public static Boolean isNull(Feedback report) {
        return report.equals(NULL);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feedback report = (Feedback) o;

        return ObjectUtils.nullSafeEquals(this.fail, report.fail) &&
                ObjectUtils.nullSafeEquals(this.exceptionListener, report.exceptionListener);

    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(this.exceptionListener) * 29 +
                ObjectUtils.nullSafeHashCode(this.fail);
    }

    public static void main(String[] args) {
        Feedback report = new Feedback();
        System.out.println(Feedback.isEmpty(report));
    }
}
