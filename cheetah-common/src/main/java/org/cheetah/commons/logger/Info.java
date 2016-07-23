package org.cheetah.commons.logger;

/**
 * Created by Max on 2016/2/2.
 */
public class Info {
    private final static Loggers logger = Loggers.logger();
    private Info(){}

    public static void log(Class<?> type, String msg, Throwable e) {
        logger.info(type, msg, e);
    }

    public static void log(Class<?> type, String msg, Object... objs) {
        logger.info(type, msg, objs);
    }

    public static void log(String moduleName, String msg, Throwable e) {
        logger.info(moduleName, msg, e);
    }

    public static void log(String moduleName, String msg, Object... objs) {
        logger.info(moduleName, msg, objs);
    }

    public static void logEnabled(Class<?> type, String msg, Throwable e) {
        logger.infoEnabled(type, msg, e);
    }

    public static void logEnabled(Class<?> type, String msg, Object... objs) {
        logger.infoEnabled(type, msg, objs);
    }

    public static void logEnabled(String moduleName, String msg, Throwable e) {
        logger.infoEnabled(moduleName, msg, e);
    }

    public static void logEnabled(String moduleName, String msg, Object... objs) {
        logger.infoEnabled(moduleName, msg, objs);
    }

    public static boolean isEnabled(Class<?> type) {
        return logger.isInfoEnabled(type);
    }

    public static boolean isEnabled(String moduleName) {
        return logger.isInfoEnabled(moduleName);
    }
}
