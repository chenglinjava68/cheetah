package org.cheetah.predator.spi.event;

import org.cheetah.commons.utils.Assert;
import org.cheetah.predator.core.Session;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EventObject;
import java.util.Objects;

/**
 * @author Max
 */
public final class SessionEvent extends EventObject {

    private static final long serialVersionUID = -7673066569265768009L;

    public enum Type {
        OPENED, APPROVED, CLOSED
    }

    private long occurredTime;
    private Session session;

    private SessionEvent(Type type, Session session) {
        super(type);
        this.session = session;
    }

    /**
     * @return
     */
    public Type type() {
        return (Type) getSource();
    }

    /**
     * @return
     */
    public Session session() {
        return session;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type(), session(), occurredTime());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }

        SessionEvent that = (SessionEvent) obj;
        return Objects.equals(type(), that.type())
                && Objects.equals(session(), that.session())
                && Objects.equals(occurredTime(), that.occurredTime());
    }

    public long occurredTime() {
        return occurredTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @param type
     * @param session
     * @return
     */
    public static SessionEvent of(Type type, Session session) {
        Assert.notNull(type, "type is null!");
        Assert.notNull(session, "session is null!");
        return new SessionEvent(type, session);
    }
}
