package jvddd.domain;

/**
 * 提供自动增长主键规则的实体基类
 * Created by Max on 2015/12/25.
 */
public class NumberKeyEntity extends AbstractEntity<NumberTrackingId> {
    public NumberKeyEntity() {
        super(new NumberTrackingId());
    }

    public NumberKeyEntity(NumberTrackingId trackingId) {
        super(trackingId);
    }

    public NumberKeyEntity(NumberTrackingId trackingId, Timist timist, Long version) {
        super(trackingId, timist, version);
    }
}
