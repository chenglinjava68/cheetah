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

    public static void debugEnabled(Class<?> type, String msg, Throwable e) {
        logger.debugEnabled(type, msg, e);
    }

    public static void debugEnabled(Class<?> type, String msg, Object... objs) {
        logger.debugEnabled(type, msg, objs);
    }

    public static void debugEnabled(String moduleName, String msg, Throwable e) {
        logger.debugEnabled(moduleName, msg, e);
    }

    public static void debugEnabled(String moduleName, String msg, Object... objs) {
        logger.debugEnabled(moduleName, msg, objs);
    }

    public static boolean isDebugEnabled(Class<?> type) {
        return logger.isDebugEnabled(type);
    }

    public static boolean isDebugEnabled(String moduleName) {
        return logger.isDebugEnabled(moduleName);
    }

}
