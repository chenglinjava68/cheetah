package javddd.domain;

/**
 * 基本实体的抽象类
 * Created by Max on 2015/12/25.
 */
public abstract class AbstractEntity implements Entity<AbstractEntity> {
    private TrackingId trackingId;
    private Timist timist = new Timist();

    AbstractEntity() {
    }

    public AbstractEntity(TrackingId trackingId) {
        this.trackingId = trackingId;
    }

    public TrackingId trackingId() {
        return trackingId;
    }

    public Timist timist() {
        return timist;
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
        return trackingId.toString();
    }

}
