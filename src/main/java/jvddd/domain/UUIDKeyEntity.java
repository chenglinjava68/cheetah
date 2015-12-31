package jvddd.domain;

import java.util.UUID;

/**
 * 提供UUID主键生成规则的的实体基类
 * Created by Max on 2015/12/25.
 */
public class UUIDKeyEntity extends AbstractEntity<UUIDTrackingId> {

    public UUIDKeyEntity() {
        super(new UUIDTrackingId(UUID.randomUUID().toString()));
    }

    public UUIDKeyEntity(UUIDTrackingId trackingId) {
        super(trackingId);
    }

    public UUIDKeyEntity(UUIDTrackingId trackingId, Timist timist, Long version) {
        super(trackingId, timist, version);
    }
}
