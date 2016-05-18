package org.cheetah.commons.logger;

/**
 * Created by Max on 2016/2/2.
 */
public class Error {
    private final static Loggers logger = Loggers.me();
    private Error(){}

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
}
