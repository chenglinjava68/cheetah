package cheetah.domain;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * 基本实体的抽象类
 * Created by Max on 2015/12/25.
 */
@MappedSuperclass
public abstract class AbstractEntity<ID extends TrackingId>
        implements OptLockEntity<AbstractEntity, ID> {
    @EmbeddedId
    private ID trackingId;
    @Embedded
    private Timist timist = new Timist();
    @Version
    private Long version;

    public AbstractEntity() {
    }

    public AbstractEntity(ID trackingId) {
        this.trackingId = trackingId;
    }

    public AbstractEntity(ID trackingId, Timist timist, Long version) {
        this.trackingId = trackingId;
        this.timist = timist;
        this.version = version;
    }

    public Timist timist() {
        return timist;
    }

    @Override
    public ID trackingId() {
        return this.trackingId;
    }

    @Override
    public Long version() {
        return this.version;
    }

    @Override
    public boolean sameAs(AbstractEntity other) {
        return other != null && trackingId.sameAs(other.trackingId);
    }

    @Override
    public void changed() {
        this.timist = timist().modify();
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
        return "[" + this.getClass().getName() + " getId=" + trackingId().toString() + " version=" + version() + "]";
    }

}
