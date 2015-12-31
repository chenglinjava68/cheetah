package jvddd.domain;

/**
 * 提供自动增长主键规则的实体基类
 * Created by Max on 2015/12/25.
 */
public class IncrementKeyEntity extends AbstractEntity<NumberTrackingId> {
    public IncrementKeyEntity() {
        super(new NumberTrackingId());
    }

    public IncrementKeyEntity(NumberTrackingId trackingId) {
        super(trackingId);
    }

    public IncrementKeyEntity(NumberTrackingId trackingId, Timist timist, Long version) {
        super(trackingId, timist, version);
    }
}
