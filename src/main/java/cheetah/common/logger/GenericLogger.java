package cheetah.common.logger;


import cheetah.common.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Max on 2016/2/2.
 */
public class GenericLogger {
    private final static GenericLogger GENERIC_LOGGER = new GenericLogger();
    private final Map<LoggerCacheKey, Logger> loggerCache = new ConcurrentHashMap<>();
    
    private GenericLogger() {
        
    }

    public static GenericLogger logger() {
        return GENERIC_LOGGER;
    }

    public void debug(Class<?> type, String msg, Throwable e) {
        this.getLoggerFrom(type).debug(msg, e);
    }

    public void debug(Class<?> type, String msg, Object... objs) {
        this.getLoggerFrom(type).debug(msg, objs);
    }

    public void info(Class<?> type, String msg, Throwable e) {
        this.getLoggerFrom(type).info(msg, e);
    }

    public void info(Class<?> type, String msg, Object... objs) {
        this.getLoggerFrom(type).info(msg, objs);
    }

    public void warn(Class<?> type, String msg, Throwable e) {
        this.getLoggerFrom(type).warn(msg, e);
    }

    public void warn(Class<?> type, String msg, Object... objs) {
        this.getLoggerFrom(type).warn(msg, objs);
    }

    public void error(Class<?> type, String msg, Throwable e) {
        this.getLoggerFrom(type).error(msg, e);
    }

    public void error(Class<?> type, String msg, Object... objs) {
        this.getLoggerFrom(type).error(msg, objs);
    }

    private Logger getLoggerFrom(Class<?> type) {
        Objects.requireNonNull(type, "type must not be null.");
        LoggerCacheKey key = LoggerCacheKey.cerateKey(type);
        if(null == this.loggerCache.get(key)) {
            this.loggerCache.putIfAbsent(key, LoggerFactory.getLogger(type));
        }

        return this.loggerCache.get(key);
    }

    private static class LoggerCacheKey {
        private Class<?> type;

        public LoggerCacheKey(Class<?> type) {
            this.type = type;
        }
        
        public static LoggerCacheKey cerateKey(Class<?> type) {
            return new LoggerCacheKey(type);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            LoggerCacheKey that = (LoggerCacheKey) o;

            return ObjectUtils.nullSafeEquals(this.type, that.type);
        }

        @Override
        public int hashCode() {
            return ObjectUtils.nullSafeHashCode(this.type) * 29;
        }
    }
}
