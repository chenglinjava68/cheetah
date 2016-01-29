package cheetah.domain.jpa;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 * Created by Max on 2015/12/31.
 */
public final class QueryHelper {
    public static <T> Expression fieldProcessing(Root<T> from, String property) {
        if (property.contains(".")) {
            String[] keys = property.split("\\.");
            return doFieldProcessing(keys, from);
        } else
            return from.get(property);
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
