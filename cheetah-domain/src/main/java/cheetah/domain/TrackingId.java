package cheetah.domain;

import java.io.Serializable;

/**
 * 模型id基类
 * Created by Max on 2015/12/25.
 */
public abstract class TrackingId<ID extends Serializable> implements ValueObject<TrackingId> {

    public abstract ID id();

    @Override
    public int hashCode() {
        return id().hashCode();
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
        return id().toString();
    }
}
