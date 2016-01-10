package cheetah.domain.hibernate;

import cheetah.domain.AbstractEntity;
import cheetah.domain.Repository;
import cheetah.domain.TrackingId;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by Max on 2015/12/31.
 */
public class HibernateRepository<I extends TrackingId, T extends AbstractEntity<I>> extends BasicHibernateRepository<I, T>
        implements Repository<I, T> {

    @Override
    public T get(I id) {
        return entityManager.find(getEntityClass(), id);
    }

    @Override
    public void put(T object) {
        entityManager.merge(object);
    }

    @Override
    public void refresh(T object) {
        entityManager.refresh(object);
    }

    @Override
    public void refresh(I objectId) {
        T entity = get(objectId);
        entity.changed();
        refresh(entity);
    }

    @Override
    public void remove(T object) {
        entityManager.remove(object);
    }

    @Override
    public void remove(I objectId) {
        T t = get(objectId);
        entityManager.remove(t);
    }

    @Override
    public T getByPropertyValue(String propertyName, Object value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        Root<T> from = criteriaQuery.from(getEntityClass());
        criteriaQuery.where(criteriaBuilder.equal(from.get(propertyName), value));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}
