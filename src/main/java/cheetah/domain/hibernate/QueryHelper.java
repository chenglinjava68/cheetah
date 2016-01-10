package cheetah.domain.hibernate;

import cheetah.domain.EntityUtils;
import cheetah.domain.NumberTrackingId;
import cheetah.domain.UUIDTrackingId;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Map;

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

    static <T> Expression fieldProcessing(Root<T> from, Map.Entry<String, ?> entry) {
        if (entry.getKey().contains(".")) {
            String[] keys = entry.getKey().split("\\.");
            return doFieldProcessing(keys, from);
        } else
            return from.get(entry.getKey());
    }

    static <T> Expression doFieldProcessing(String[] keys, Root<T> from) {
        switch (keys.length) {
            case 2:
                return FromHelper.get(keys[0], keys[1], from);
            case 3:
                return FromHelper.get(keys[0], keys[1], keys[2], from);
            case 4:
                return FromHelper.get(keys[0], keys[1], keys[2], keys[3], from);
            case 5:
                return FromHelper.get(keys[0], keys[1], keys[2], keys[3], keys[4], from);
            case 6:
                return FromHelper.get(keys[0], keys[1], keys[2], keys[3], keys[4], keys[5], from);
            case 7:
                return FromHelper.get(keys[0], keys[1], keys[2], keys[3], keys[4], keys[5], keys[6], from);
            case 8:
                return FromHelper.get(keys[0], keys[1], keys[2], keys[3], keys[4], keys[5], keys[6], keys[7], from);
            default:
                throw new RuntimeException("doFieldProcessing failure");
        }
    }


}
