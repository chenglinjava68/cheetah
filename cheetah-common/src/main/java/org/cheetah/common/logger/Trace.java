package org.cheetah.common.logger;

/**
 * Created by maxhuang on 2016/8/3.
 */
public class Trace {
    private final static Loggers logger = Loggers.logger();
    private Trace(){}

    public static void log(Class<?> type, String msg, Throwable e) {
        logger.trace(type, msg, e);
    }

    public static void log(Class<?> type, String msg, Object... objs) {
        logger.trace(type, msg, objs);
    }

    public static void log(String moduleName, String msg, Throwable e) {
        logger.trace(moduleName, msg, e);
    }

    public static void log(String moduleName, String msg, Object... objs) {
        logger.trace(moduleName, msg, objs);
    }

    public static void logEnabled(Class<?> type, String msg, Throwable e) {
        logger.traceEnabled(type, msg, e);
    }

    public static void logEnabled(Class<?> type, String msg, Object... objs) {
        logger.traceEnabled(type, msg, objs);
    }

    public static void logEnabled(String moduleName, String msg, Throwable e) {
        logger.traceEnabled(moduleName, msg, e);
    }

    public static void logEnabled(String moduleName, String msg, Object... objs) {
        logger.traceEnabled(moduleName, msg, objs);
    }

    public static boolean isEnabled(Class<?> type) {
        return logger.isTraceEnabled(type);
    }

    public static boolean isEnabled(String moduleName) {
        return logger.isTraceEnabled(moduleName);
    }
}
