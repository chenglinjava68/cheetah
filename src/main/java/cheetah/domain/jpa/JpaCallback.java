package cheetah.domain.jpa;

import cheetah.domain.Enquirer;

import javax.persistence.EntityManager;

/**
 * Created by Max on 2016/1/10.
 */
public interface JpaCallback<T> {
    T doCallback(EntityManager entityManager, Enquirer querier);
}
