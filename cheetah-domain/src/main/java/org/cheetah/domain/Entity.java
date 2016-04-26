package org.cheetah.domain;

import java.io.Serializable;

/**
 * 实体最顶层接口
 * Created by Max on 2015/12/22.
 */
public interface Entity<T, ID extends Serializable> extends Serializable {
    boolean sameAs(T other);

    void changed();

    ID trackingId();
}
