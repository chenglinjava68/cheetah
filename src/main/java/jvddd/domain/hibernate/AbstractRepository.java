package jvddd.domain.hibernate;

import jvddd.domain.AbstractEntity;
import jvddd.domain.DomainUtils;
import jvddd.domain.TrackingId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Max on 2016/1/9.
 */
public abstract class AbstractRepository<I extends TrackingId, T extends AbstractEntity<I>> {
    private Class<T> entityClass;

    public AbstractRepository() {
        entityClass = DomainUtils.getType(getClass(), 1);
    }

    @PersistenceContext
    protected EntityManager entityManager;

    public Class<T> getEntityClass() {
        return entityClass;
    }


}
