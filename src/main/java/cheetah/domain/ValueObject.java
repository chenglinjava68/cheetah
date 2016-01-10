package cheetah.domain;

import java.io.Serializable;

/**
 * 值对象接口
 * Created by Max on 2015/12/22.
 */
public interface ValueObject<T> extends Serializable {
    boolean sameAs(T other);
}
