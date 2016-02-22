package cheetah.distributor.machine;


import cheetah.util.ObjectUtils;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/21.
 */
public class Report {
    public static final Report EMPTY = new Report();
    public static final Report NULL = new Report(); //不需要结果
    public static final Report SUCCESS = new Report();
    public static final Report FAILURE = new Report();
    private boolean fail;
    private Class<? extends EventListener> exceptionListener;

    public Report() {
    }

    public Report(boolean fail, Class<? extends EventListener> exceptionListener) {
        this.fail = fail;
        this.exceptionListener = exceptionListener;
    }

    public boolean isFail() {
        return fail;
    }

    public Class<? extends EventListener> getExceptionListener() {
        return exceptionListener;
    }

    public static Boolean isEmpty(Report report) {
        return report.equals(EMPTY);
    }

    public static Boolean isNull(Report report) {
        return report.equals(NULL);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report report = (Report) o;

        return ObjectUtils.nullSafeEquals(this.fail, report.fail) &&
                ObjectUtils.nullSafeEquals(this.exceptionListener, report.exceptionListener);

    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(this.exceptionListener) * 29 +
                ObjectUtils.nullSafeHashCode(this.fail);
    }

    public static void main(String[] args) {
        Report report = new Report();
        System.out.println(Report.isEmpty(report));
    }
}
