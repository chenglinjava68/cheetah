package javddd.domain;

import java.io.Serializable;

/**
 * 基本实体的抽象类
 * Created by Max on 2015/12/25.
 */
public abstract class AbstractEntity<ID extends Serializable>
        implements OptLockEntity<AbstractEntity, ID> {
    private TrackingId<ID> trackingId;
    private Timist timist = new Timist();
    private Long version;

    public AbstractEntity() {
    }

    public AbstractEntity(TrackingId<ID> trackingId) {
        this.trackingId = trackingId;
    }

    public AbstractEntity(TrackingId<ID> trackingId, Timist timist, Long version) {
        this.trackingId = trackingId;
        this.timist = timist;
        this.version = version;
    }

    public Timist timist() {
        return timist;
    }

    @Override
    public TrackingId<ID> getId() {
        return this.trackingId;
    }

    @Override
    public Long getVersion() {
        return this.version;
    }

    @Override
    public boolean sameAs(AbstractEntity other) {
        return other != null && trackingId.sameAs(other.trackingId);
    }

    @Override
    public void changed() {
        this.timist = new Timist(this.timist.createTime(), System.currentTimeMillis());
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;

        final AbstractEntity other = (AbstractEntity) object;
        return sameAs(other);
    }

    @Override
    public int hashCode() {
        return trackingId.hashCode();
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + " getId=" + getId() + " version=" + getVersion() + "]";
    }

}
