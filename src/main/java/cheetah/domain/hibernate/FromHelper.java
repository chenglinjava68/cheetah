package cheetah.domain.hibernate;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 * Created by Max on 2016/1/7.
 */
public final class FromHelper {

    public static <T> Expression get(String key1, String key2, Root<T> from) {
        return from.get(key1).get(key2);
    }

    public static <T> Expression get(String key1, String key2, String key3, Root<T> from) {
        return from.get(key1).get(key2).get(key3);
    }

    public static <T> Expression get(String key1, String key2, String key3, String key4, Root<T> from) {
        return from.get(key1).get(key2).get(key3).get(key4);
    }

    public static <T> Expression get(String key1, String key2, String key3, String key4, String key5, Root<T> from) {
        return from.get(key1).get(key2).get(key3).get(key4).get(key5);
    }

    public static <T> Expression get(String key1, String key2, String key3, String key4, String key5, String key6, Root<T> from) {
        return from.get(key1).get(key2).get(key3).get(key4).get(key5).get(key6);
    }

    public static <T> Expression get(String key1, String key2, String key3, String key4, String key5, String key6, String key7, Root<T> from) {
        return from.get(key1).get(key2).get(key3).get(key4).get(key5).get(key6).get(key7);
    }

    public static <T> Expression get(String key1, String key2, String key3, String key4, String key5, String key6, String key7, String key8, Root<T> from) {
        return from.get(key1).get(key2).get(key3).get(key4).get(key5).get(key6).get(key7).get(key8);
    }

}
