package javddd.domain;

import java.io.Serializable;

/**
 * Created by Max on 2015/12/30.
 */
public interface OptLockEntity<T, ID extends Serializable>
        extends Entity<T, ID> {
    Long getVersion();

}
