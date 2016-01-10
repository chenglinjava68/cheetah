package cheetah.domain;

import java.io.Serializable;

/**
 * ֵ����ӿ�
 * Created by Max on 2015/12/22.
 */
public interface ValueObject<T> extends Serializable {
    boolean sameAs(T other);
}
