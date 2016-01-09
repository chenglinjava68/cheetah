package cheetah.domain.hibernate;

import cheetah.domain.AbstractEntity;
import cheetah.domain.DomainUtils;
import cheetah.domain.TrackingId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Max on 2016/1/9.
 */
public abstract class BasicRepository<I extends TrackingId, T extends AbstractEntity<I>> {
    private Class<T> entityClass;

    public BasicRepository() {
        entityClass = DomainUtils.getType(getClass(), 1);
    }

    @PersistenceContext
    protected EntityManager entityManager;

    public Class<T> getEntityClass() {
        return entityClass;
    }


}
