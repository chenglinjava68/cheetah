package cheetah.common.logger;

/**
 * Created by Max on 2016/2/2.
 */
public class Error {
    private final static GenericLogger logger = GenericLogger.logger();
    private Error(){}

    public static void log(Class<?> type, String msg, Throwable e) {
        logger.error(type, msg, e);
    }

    public static void log(Class<?> type, String msg, Object... objs) {
        logger.error(type, msg, objs);
    }
}
