package org.cheetah.commons.logger;

        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

        import java.util.Objects;
        import java.util.concurrent.ConcurrentHashMap;
        import java.util.concurrent.ConcurrentMap;

/**
 * <p>Loggers class.</p>
 *
 * @author Max
 * @version $Id: $Id
 */
public final class Loggers {

    private static final Loggers ME = new Loggers();
    private final ConcurrentMap<Object, Logger> loggers = new ConcurrentHashMap<>();

    /**
     * <p>me.</p>
     *
     * @return a {@link Loggers} object.
     */
    public static Loggers me() {
        return ME;
    }

    /**
     * <p>debug.</p>
     *
     * @param type a {@link Class} object.
     * @param msg a {@link String} object.
     * @param e a {@link Throwable} object.
     */
    public void debug(Class<?> type, String msg, Throwable e) {
        of(type).debug(msg, e);
    }
    public void debug(String moduleName, String msg, Throwable e) {
        of(moduleName).debug(msg, e);
    }

    /**
     * <p>debug.</p>
     *
     * @param type a {@link Class} object.
     * @param msg a {@link String} object.
     * @param objs a {@link Object} object.
     */
    public void debug(Class<?> type, String msg, Object... objs) {
        of(type).debug(msg, objs);
    }
    public void debug(String moduleName, String msg, Object... objs) {
        of(moduleName).debug(msg, objs);
    }

    /**
     * <p>info.</p>
     *
     * @param type a {@link Class} object.
     * @param msg a {@link String} object.
     * @param e a {@link Throwable} object.
     */
    public void info(Class<?> type, String msg, Throwable e) {
        of(type).info(msg, e);
    }
    public void info(String moduleName, String msg, Throwable e) {
        of(moduleName).info(msg, e);
    }

    /**
     * <p>info.</p>
     *
     * @param type a {@link Class} object.
     * @param msg a {@link String} object.
     * @param objs a {@link Object} object.
     */
    public void info(Class<?> type, String msg, Object... objs) {
        of(type).info(msg, objs);
    }
    public void info(String moduleName, String msg, Object... objs) {
        of(moduleName).info(msg, objs);
    }


    /**
     * <p>warn.</p>
     *
     * @param type a {@link Class} object.
     * @param msg a {@link String} object.
     * @param e a {@link Throwable} object.
     */
    public void warn(Class<?> type, String msg, Throwable e) {
        of(type).warn(msg, e);
    }
    public void warn(String moduleName, String msg, Throwable e) {
        of(moduleName).warn(msg, e);
    }

    /**
     * <p>warn.</p>
     *
     * @param type a {@link Class} object.
     * @param msg a {@link String} object.
     * @param objs a {@link Object} object.
     */
    public void warn(Class<?> type, String msg, Object... objs) {
        of(type).warn(msg, objs);
    }
    public void warn(String moduleName, String msg, Object... objs) {
        of(moduleName).warn(msg, objs);
    }

    /**
     * <p>error.</p>
     *
     * @param type a {@link Class} object.
     * @param msg a {@link String} object.
     * @param e a {@link Throwable} object.
     */
    public void error(Class<?> type, String msg, Throwable e) {
        of(type).error(msg, e);
    }
    public void error(String moduleName, String msg, Throwable e) {
        of(moduleName).error(msg, e);
    }

    /**
     * <p>error.</p>
     *
     * @param type a {@link Class} object.
     * @param msg a {@link String} object.
     * @param objs a {@link Object} object.
     */
    public void error(Class<?> type, String msg, Object... objs) {
        of(type).error(msg, objs);
    }
    public void error(String moduleName, String msg, Object... objs) {
        of(moduleName).error(msg, objs);
    }

    /**
     *
     * @param type
     * @param msg
     * @param e
     */
    public void debugEnabled(Class<?> type, String msg, Throwable e) {
        Logger log = of(type);
        if(log.isDebugEnabled())
            log.debug(msg, e);
    }

    public void debugEnabled(String moduleName, String msg, Throwable e) {
        Logger log = of(moduleName);
        if(log.isDebugEnabled())
            log.debug(msg, e);
    }

    /**
     *
     * @param type
     * @param msg
     * @param objs
     */
    public void debugEnabled(Class<?> type, String msg, Object... objs) {
        Logger log = of(type);
        if(log.isDebugEnabled())
            log.debug(msg, objs);
    }

    public void debugEnabled(String moduleName, String msg, Object... objs) {
        Logger log = of(moduleName);
        if(log.isDebugEnabled())
            log.debug(msg, objs);
    }

    public void infoEnabled(Class<?> type, String msg, Throwable e) {
        Logger log = of(type);
        if(log.isInfoEnabled())
            log.info(msg, e);
    }

    public void infoEnabled(String moduleName, String msg, Throwable e) {
        Logger log = of(moduleName);
        if(log.isInfoEnabled())
            log.info(msg, e);
    }

    public void infoEnabled(Class<?> type, String msg, Object... objs) {
        Logger log = of(type);
        if(log.isInfoEnabled())
            log.info(msg, objs);

    }

    public void infoEnabled(String moduleName, String msg, Object... objs) {
        Logger log = of(moduleName);
        if(log.isInfoEnabled())
            log.info(msg, objs);
    }

    public void warnEnabled(Class<?> type, String msg, Throwable e) {
        Logger log = of(type);
        if(log.isWarnEnabled())
            log.warn(msg, e);

    }
    public void warnEnabled(String moduleName, String msg, Throwable e) {
        Logger log = of(moduleName);
        if(log.isWarnEnabled())
            log.warn(msg, e);
    }

    public void warnEnabled(Class<?> type, String msg, Object... objs) {
        Logger log = of(type);
        if(log.isWarnEnabled())
            log.warn(msg, objs);
    }

    public void warnEnabled(String moduleName, String msg, Object... objs) {
        Logger log = of(moduleName);
        if(log.isWarnEnabled())
            log.warn(msg, objs);
    }

    public void errorEnabled(Class<?> type, String msg, Throwable e) {
        Logger log = of(type);
        if(log.isErrorEnabled())
            log.error(msg, e);

    }

    public void errorEnabled(String moduleName, String msg, Throwable e) {
        Logger log = of(moduleName);
        if(log.isErrorEnabled())
            log.error(msg, e);
    }

    public void errorEnabled(Class<?> type, String msg, Object... objs) {
        Logger log = of(type);
        if(log.isErrorEnabled())
            log.error(msg, objs);

    }

    public void errorEnabled(String moduleName, String msg, Object... objs) {
        Logger log = of(moduleName);
        if(log.isErrorEnabled())
            log.error(msg, objs);
    }

    public Logger of(Class<?> type) {
        Objects.requireNonNull(type, "type must not be null.");
        if (null == loggers.get(type)) {
            loggers.putIfAbsent(type, LoggerFactory.getLogger(type));
        }

        return loggers.get(type);
    }

    public Logger of(String moduleName) {
        Objects.requireNonNull(moduleName, "type must not be null.");
        if (null == loggers.get(moduleName)) {
            loggers.putIfAbsent(moduleName, LoggerFactory.getLogger(moduleName));
        }

        return loggers.get(moduleName);
    }


    private Loggers() {
    }
}
