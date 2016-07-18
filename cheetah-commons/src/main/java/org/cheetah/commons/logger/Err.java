package org.cheetah.commons.logger;

/**
 * Created by Max on 2016/2/2.
 */
public class Err {
    private final static Loggers logger = Loggers.me();
    private Err(){}

    public static void log(Class<?> type, String msg, Throwable e) {
        logger.error(type, msg, e);
    }

    public static void log(Class<?> type, String msg, Object... objs) {
        logger.error(type, msg, objs);
    }

    public static void log(String moduleName, String msg, Throwable e) {
        logger.error(moduleName, msg, e);
    }

    public static void log(String moduleName, String msg, Object... objs) {
        logger.error(moduleName, msg, objs);
    }

    public static void errorEnabled(Class<?> type, String msg, Throwable e) {
        logger.errorEnabled(type, msg, e);
    }

    public static void errorEnabled(Class<?> type, String msg, Object... objs) {
        logger.errorEnabled(type, msg, objs);
    }

    public static void errorEnabled(String moduleName, String msg, Throwable e) {
        logger.errorEnabled(moduleName, msg, e);
    }

    public static void errorEnabled(String moduleName, String msg, Object... objs) {
        logger.errorEnabled(moduleName, msg, objs);
    }

    public static boolean isErrorEnabled(Class<?> type) {
        return logger.isErrorEnabled(type);
    }

    public static boolean isErrorEnabled(String moduleName) {
        return logger.isErrorEnabled(moduleName);
    }

}
