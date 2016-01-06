package jvddd.domain.hibernate;

import jvddd.domain.AbstractEntity;
import jvddd.domain.Repository;
import jvddd.domain.TrackingId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Max on 2015/12/31.
 */
public abstract class AbstractHibernateRepository<I extends TrackingId, T extends AbstractEntity<I>>
        implements Repository<I, T> {

    private Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    public AbstractHibernateRepository() {

        Class<?> c = getClass();

        Type type = c.getGenericSuperclass();

        if (type instanceof ParameterizedType) {

            Type[] parameterizedType = ((ParameterizedType) type)

                    .getActualTypeArguments();

            entityClass = (Class<T>) parameterizedType[1];
        }
    }

    @Override
    public T get(I id) {
        return entityManager.find(entityClass, id);
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
    public List<T> list() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        criteriaQuery.from(this.entityClass);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public T getByPropertyValue(String propertyName, Object value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> from = criteriaQuery.from(this.entityClass);
        criteriaQuery.where(criteriaBuilder.equal(from.get(propertyName), value));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}
