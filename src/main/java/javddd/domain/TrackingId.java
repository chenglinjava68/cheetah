package javddd.domain;

import java.io.Serializable;

/**
 * 实体的唯一标示基类
 * Created by Max on 2015/12/25.
 */
public abstract class TrackingId<ID extends Serializable> implements ValueObject<TrackingId> {
    protected ID id;

    TrackingId() {
    }

    public TrackingId(ID id) {
        this.id = id;
    }

    public ID id() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        TrackingId other = (TrackingId) obj;

        return sameAs(other);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
