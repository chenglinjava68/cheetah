package cheetah.commons.logger;

/**
 * Created by Max on 2016/2/2.
 */
public class Debug {
    private final static GenericLogger logger = GenericLogger.logger();
    private Debug(){}

    public static void log(Class<?> type, String msg, Throwable e) {
        logger.debug(type, msg, e);
    }

    public static void log(Class<?> type, String msg, Object... objs) {
        logger.debug(type, msg, objs);
    }

}
