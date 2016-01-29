package cheetah.domain.specification;

import cheetah.domain.Enquirer;
import cheetah.domain.QueryAgreement;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Max on 2016/1/11.
 */
public class JpaSpecificationImpl implements JpaCompositeSpecification {
    private List<QueryAgreement> jpaQueryAgreeds;

    public <T> void and(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {

    }

    public <T> void or(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {

    }

    public Iterator<QueryAgreement> iterator() {
        return new QueryAgreedIterator();
    }

    public class QueryAgreedIterator implements Iterator<QueryAgreement> {
        private int cursor;
        @Override
        public boolean hasNext() {
            return cursor < jpaQueryAgreeds.size();
        }

        @Override
        public QueryAgreement next() {
            if(cursor < jpaQueryAgreeds.size())
                return jpaQueryAgreeds.get(cursor++);
            return null;
        }
    }
}
