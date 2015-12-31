package jvddd.domain;

/**
 * Created by Max on 2015/12/31.
 */
public final class QueryHelper {
    public static UUIDTrackingId createQueryTrackingId(String trackingId) {
        return EntityUtils.createTrackingId(trackingId);
    }

    public static NumberTrackingId createTrackingId(Long trackingId) {
        return EntityUtils.createTrackingId(trackingId);
    }
}
