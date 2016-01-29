package cheetah.domain.jpa;

import cheetah.domain.Enquirer;
import cheetah.domain.QueryAgreement;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Max on 2016/1/11.
 */
public interface JpaCompositeQueryAgreed extends QueryAgreement {
    <T> void and(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

    <T> void or(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates);

}
