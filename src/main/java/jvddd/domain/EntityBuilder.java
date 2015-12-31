package jvddd.domain;

/**
 * Created by Max on 2015/12/31.
 */
public abstract class EntityBuilder<ID extends TrackingId, E extends AbstractEntity> {
    protected ID trackingId;
    protected Timist timist = new Timist();
    protected Long version;

    public abstract E build();

    public EntityBuilder trackingId(ID trackingId) {
        this.trackingId = trackingId;
        return this;
    }

    public EntityBuilder timist(Timist timist) {
        this.timist = timist;
        return this;
    }

    public EntityBuilder version(Long version) {
        this.version = version;
        return this;
    }
}
