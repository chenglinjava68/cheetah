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
    private final ConcurrentMap<Class<?>, Logger> loggers = new ConcurrentHashMap<>();

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
        ofLogger(type).debug(msg, e);
    }

    /**
     * <p>debug.</p>
     *
     * @param type a {@link Class} object.
     * @param msg a {@link String} object.
     * @param objs a {@link Object} object.
     */
    public void debug(Class<?> type, String msg, Object... objs) {
        ofLogger(type).debug(msg, objs);
    }

    /**
     * <p>info.</p>
     *
     * @param type a {@link Class} object.
     * @param msg a {@link String} object.
     * @param e a {@link Throwable} object.
     */
    public void info(Class<?> type, String msg, Throwable e) {
        ofLogger(type).info(msg, e);
    }

    /**
     * <p>info.</p>
     *
     * @param type a {@link Class} object.
     * @param msg a {@link String} object.
     * @param objs a {@link Object} object.
     */
    public void info(Class<?> type, String msg, Object... objs) {
        ofLogger(type).info(msg, objs);
    }


    /**
     * <p>warn.</p>
     *
     * @param type a {@link Class} object.
     * @param msg a {@link String} object.
     * @param e a {@link Throwable} object.
     */
    public void warn(Class<?> type, String msg, Throwable e) {
        ofLogger(type).warn(msg, e);
    }

    /**
     * <p>warn.</p>
     *
     * @param type a {@link Class} object.
     * @param msg a {@link String} object.
     * @param objs a {@link Object} object.
     */
    public void warn(Class<?> type, String msg, Object... objs) {
        ofLogger(type).warn(msg, objs);
    }

    /**
     * <p>error.</p>
     *
     * @param type a {@link Class} object.
     * @param msg a {@link String} object.
     * @param e a {@link Throwable} object.
     */
    public void error(Class<?> type, String msg, Throwable e) {
        ofLogger(type).error(msg, e);
    }

    /**
     * <p>error.</p>
     *
     * @param type a {@link Class} object.
     * @param msg a {@link String} object.
     * @param objs a {@link Object} object.
     */
    public void error(Class<?> type, String msg, Object... objs) {
        ofLogger(type).error(msg, objs);
    }

    private Logger ofLogger(Class<?> type) {
        Objects.requireNonNull(type, "type must not be null.");
        if (null == loggers.get(type)) {
            loggers.putIfAbsent(type, LoggerFactory.getLogger(type));
        }

        return loggers.get(type);
    }

    private Loggers() {
    }
}
