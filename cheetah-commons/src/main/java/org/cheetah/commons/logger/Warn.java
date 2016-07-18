package org.cheetah.commons.logger;

/**
 * Created by Max on 2016/2/2.
 */
public class Warn {
    private final static Loggers logger = Loggers.me();
    private Warn(){}

    public static void log(Class<?> type, String msg, Throwable e) {
        logger.warn(type, msg, e);
    }

    public static void log(Class<?> type, String msg, Object... objs) {
        logger.warn(type, msg, objs);
    }

    public static void log(String moduleName, String msg, Throwable e) {
        logger.warn(moduleName, msg, e);
    }

    public static void log(String moduleName, String msg, Object... objs) {
        logger.warn(moduleName, msg, objs);
    }

    public static void logEnabled(Class<?> type, String msg, Throwable e) {
        logger.warnEnabled(type, msg, e);
    }

    public static void logEnabled(Class<?> type, String msg, Object... objs) {
        logger.warnEnabled(type, msg, objs);
    }

    public static void logEnabled(String moduleName, String msg, Throwable e) {
        logger.warnEnabled(moduleName, msg, e);
    }

    public static void logEnabled(String moduleName, String msg, Object... objs) {
        logger.warnEnabled(moduleName, msg, objs);
    }

    public static boolean isEnabled(Class<?> type) {
        return logger.isWarnEnabled(type);
    }

    public static boolean isEnabled(String moduleName) {
        return logger.isWarnEnabled(moduleName);
    }

}
