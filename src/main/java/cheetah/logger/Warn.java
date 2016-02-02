package cheetah.logger;

/**
 * Created by Max on 2016/2/2.
 */
public class Warn {
    private final static GenericLogger logger = GenericLogger.logger();
    private Warn(){}

    public static void log(Class<?> type, String msg, Throwable e) {
        logger.warn(type, msg, e);
    }

    public static void log(Class<?> type, String msg, Object... objs) {
        logger.warn(type, msg, objs);
    }
}
