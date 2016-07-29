package org.cheetah.common.logger;

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

    private static final Loggers logger = new Loggers();
    private final ConcurrentMap<Object, Logger> loggers = new ConcurrentHashMap<>();

    /**
     * <p>logger.</p>
     *
     * @return a {@link Loggers} object.
     */
    public static Loggers logger() {
        return logger;
    }

    /**
     * <p>debug.</p>
     *
     * @param type a {@link Class} object.
     * @param msg  a {@link String} object.
     * @param e    a {@link Throwable} object.
     */
    public void debug(Class<?> type, String msg, Throwable e) {
        getLogger(type).debug(msg, e);
    }

    /**
     *
     * @param moduleName
     * @param msg
     * @param e
     */
    public void debug(String moduleName, String msg, Throwable e) {
        getLogger(moduleName).debug(msg, e);
    }

    /**
     * <p>debug.</p>
     *
     * @param type a {@link Class} object.
     * @param msg  a {@link String} object.
     * @param objs a {@link Object} object.
     */
    public void debug(Class<?> type, String msg, Object... objs) {
        getLogger(type).debug(msg, objs);
    }

    /**
     *
     * @param moduleName
     * @param msg
     * @param objs
     */
    public void debug(String moduleName, String msg, Object... objs) {
        getLogger(moduleName).debug(msg, objs);
    }

    /**
     * @param type
     * @param msg
     * @param e
     */
    public void debugEnabled(Class<?> type, String msg, Throwable e) {
        Logger log = getLogger(type);
        if (log.isDebugEnabled())
            log.debug(msg, e);
    }

    public void debugEnabled(String moduleName, String msg, Throwable e) {
        Logger log = getLogger(moduleName);
        if (log.isDebugEnabled())
            log.debug(msg, e);
    }

    /**
     * @param type
     * @param msg
     * @param objs
     */
    public void debugEnabled(Class<?> type, String msg, Object... objs) {
        Logger log = getLogger(type);
        if (log.isDebugEnabled())
            log.debug(msg, objs);
    }

    /**
     *
     * @param moduleName
     * @param msg
     * @param objs
     */
    public void debugEnabled(String moduleName, String msg, Object... objs) {
        Logger log = getLogger(moduleName);
        if (log.isDebugEnabled())
            log.debug(msg, objs);
    }

    /**
     * @param type
     */
    public boolean isDebugEnabled(Class<?> type) {
        Logger log = getLogger(type);
        return log.isDebugEnabled();
    }

    /**
     * @param moduleName
     */
    public boolean isDebugEnabled(String moduleName) {
        Logger log = getLogger(moduleName);
        return log.isDebugEnabled();
    }


    /**
     * <p>info.</p>
     *
     * @param type a {@link Class} object.
     * @param msg  a {@link String} object.
     * @param e    a {@link Throwable} object.
     */
    public void info(Class<?> type, String msg, Throwable e) {
        getLogger(type).info(msg, e);
    }

    public void info(String moduleName, String msg, Throwable e) {
        getLogger(moduleName).info(msg, e);
    }

    /**
     * <p>info.</p>
     *
     * @param type a {@link Class} object.
     * @param msg  a {@link String} object.
     * @param objs a {@link Object} object.
     */
    public void info(Class<?> type, String msg, Object... objs) {
        getLogger(type).info(msg, objs);
    }

    public void info(String moduleName, String msg, Object... objs) {
        getLogger(moduleName).info(msg, objs);
    }

    /**
     *
     * @param type
     * @param msg
     * @param e
     */
    public void infoEnabled(Class<?> type, String msg, Throwable e) {
        Logger log = getLogger(type);
        if (log.isInfoEnabled())
            log.info(msg, e);
    }

    /**
     *
     * @param moduleName
     * @param msg
     * @param e
     */
    public void infoEnabled(String moduleName, String msg, Throwable e) {
        Logger log = getLogger(moduleName);
        if (log.isInfoEnabled())
            log.info(msg, e);
    }

    /**
     *
     * @param type
     * @param msg
     * @param objs
     */
    public void infoEnabled(Class<?> type, String msg, Object... objs) {
        Logger log = getLogger(type);
        if (log.isInfoEnabled())
            log.info(msg, objs);

    }

    /**
     *
     * @param moduleName
     * @param msg
     * @param objs
     */
    public void infoEnabled(String moduleName, String msg, Object... objs) {
        Logger log = getLogger(moduleName);
        if (log.isInfoEnabled())
            log.info(msg, objs);
    }

    /**
     *
     * @param type
     * @return
     */
    public boolean isInfoEnabled(Class<?> type) {
        Logger log = getLogger(type);
        return log.isInfoEnabled();
    }

    /**
     *
     * @param moduleName
     * @return
     */
    public boolean isInfoEnabled(String moduleName) {
        Logger log = getLogger(moduleName);
        return log.isInfoEnabled();
    }

    /**
     * <p>warn.</p>
     *
     * @param type a {@link Class} object.
     * @param msg  a {@link String} object.
     * @param e    a {@link Throwable} object.
     */
    public void warn(Class<?> type, String msg, Throwable e) {
        getLogger(type).warn(msg, e);
    }

    public void warn(String moduleName, String msg, Throwable e) {
        getLogger(moduleName).warn(msg, e);
    }

    /**
     * <p>warn.</p>
     *
     * @param type a {@link Class} object.
     * @param msg  a {@link String} object.
     * @param objs a {@link Object} object.
     */
    public void warn(Class<?> type, String msg, Object... objs) {
        getLogger(type).warn(msg, objs);
    }

    /**
     *
     * @param moduleName
     * @param msg
     * @param objs
     */
    public void warn(String moduleName, String msg, Object... objs) {
        getLogger(moduleName).warn(msg, objs);
    }

    /**
     *
     * @param type
     * @param msg
     * @param e
     */
    public void warnEnabled(Class<?> type, String msg, Throwable e) {
        Logger log = getLogger(type);
        if (log.isWarnEnabled())
            log.warn(msg, e);

    }

    /**
     *
     * @param moduleName
     * @param msg
     * @param e
     */
    public void warnEnabled(String moduleName, String msg, Throwable e) {
        Logger log = getLogger(moduleName);
        if (log.isWarnEnabled())
            log.warn(msg, e);
    }

    /**
     *
     * @param type
     * @param msg
     * @param objs
     */
    public void warnEnabled(Class<?> type, String msg, Object... objs) {
        Logger log = getLogger(type);
        if (log.isWarnEnabled())
            log.warn(msg, objs);
    }

    /**
     *
     * @param moduleName
     * @param msg
     * @param objs
     */
    public void warnEnabled(String moduleName, String msg, Object... objs) {
        Logger log = getLogger(moduleName);
        if (log.isWarnEnabled())
            log.warn(msg, objs);
    }

    /**
     *
     * @param type
     * @return
     */
    public boolean isWarnEnabled(Class<?> type) {
        Logger log = getLogger(type);
        return log.isWarnEnabled();
    }

    /**
     *
     * @param moduleName
     * @return
     */
    public boolean isWarnEnabled(String moduleName) {
        Logger log = getLogger(moduleName);
        return log.isWarnEnabled();
    }
    /**
     * <p>error.</p>
     *
     * @param type a {@link Class} object.
     * @param msg  a {@link String} object.
     * @param e    a {@link Throwable} object.
     */
    public void error(Class<?> type, String msg, Throwable e) {
        getLogger(type).error(msg, e);
    }

    public void error(String moduleName, String msg, Throwable e) {
        getLogger(moduleName).error(msg, e);
    }

    /**
     * <p>error.</p>
     *
     * @param type a {@link Class} object.
     * @param msg  a {@link String} object.
     * @param objs a {@link Object} object.
     */
    public void error(Class<?> type, String msg, Object... objs) {
        getLogger(type).error(msg, objs);
    }

    /**
     *
     * @param moduleName
     * @param msg
     * @param objs
     */
    public void error(String moduleName, String msg, Object... objs) {
        getLogger(moduleName).error(msg, objs);
    }

    /**
     *
     * @param type
     * @param msg
     * @param e
     */
    public void errorEnabled(Class<?> type, String msg, Throwable e) {
        Logger log = getLogger(type);
        if (log.isErrorEnabled())
            log.error(msg, e);

    }

    /**
     *
     * @param moduleName
     * @param msg
     * @param e
     */
    public void errorEnabled(String moduleName, String msg, Throwable e) {
        Logger log = getLogger(moduleName);
        if (log.isErrorEnabled())
            log.error(msg, e);
    }

    /**
     *
     * @param type
     * @param msg
     * @param objs
     */
    public void errorEnabled(Class<?> type, String msg, Object... objs) {
        Logger log = getLogger(type);
        if (log.isErrorEnabled())
            log.error(msg, objs);

    }

    /**
     *
     * @param moduleName
     * @param msg
     * @param objs
     */
    public void errorEnabled(String moduleName, String msg, Object... objs) {
        Logger log = getLogger(moduleName);
        if (log.isErrorEnabled())
            log.error(msg, objs);
    }

    /**
     *
     * @param type
     * @return
     */
    public boolean isErrorEnabled(Class<?> type) {
        Logger log = getLogger(type);
        return log.isErrorEnabled();
    }

    /**
     *
     * @param moduleName
     * @return
     */
    public boolean isErrorEnabled(String moduleName) {
        Logger log = getLogger(moduleName);
        return log.isErrorEnabled();
    }

    /**
     *
     * @param type
     * @return
     */
    public Logger getLogger(Class<?> type) {
        Objects.requireNonNull(type, "type must not be null.");
        if (null == loggers.get(type)) {
            loggers.putIfAbsent(type, LoggerFactory.getLogger(type));
        }

        return loggers.get(type);
    }

    /**
     *
     * @param moduleName
     * @return
     */
    public Logger getLogger(String moduleName) {
        Objects.requireNonNull(moduleName, "type must not be null.");
        if (null == loggers.get(moduleName)) {
            loggers.putIfAbsent(moduleName, LoggerFactory.getLogger(moduleName));
        }

        return loggers.get(moduleName);
    }


    private Loggers() {
    }
}
