package org.cheetah.commons.logger;

/**
 * Created by Max on 2016/2/2.
 */
public class Debug {
    private final static Loggers logger = Loggers.me();
    private Debug(){}

    public static void log(Class<?> type, String msg, Throwable e) {
        logger.debug(type, msg, e);
    }
    public static void log(String moduleName, String msg, Throwable e) {
        logger.debug(moduleName, msg, e);
    }

    public static void log(Class<?> type, String msg, Object... objs) {
        logger.debug(type, msg, objs);
    }
    public static void log(String moduleName, String msg, Object... objs) {
        logger.debug(moduleName, msg, objs);
    }

    public static void logEnabled(Class<?> type, String msg, Throwable e) {
        logger.debugEnabled(type, msg, e);
    }

    public static void logEnabled(Class<?> type, String msg, Object... objs) {
        logger.debugEnabled(type, msg, objs);
    }

    public static void logEnabled(String moduleName, String msg, Throwable e) {
        logger.debugEnabled(moduleName, msg, e);
    }

    public static void logEnabled(String moduleName, String msg, Object... objs) {
        logger.debugEnabled(moduleName, msg, objs);
    }

    public static boolean isEnabled(Class<?> type) {
        return logger.isDebugEnabled(type);
    }

    public static boolean isEnabled(String moduleName) {
        return logger.isDebugEnabled(moduleName);
    }

}
