package cheetah.domain.hibernate;

import cheetah.domain.AmpleQuerier;
import cheetah.domain.PageRequest;
import cheetah.domain.Querier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Max on 2016/1/10.
 */
public class HibernateQueryInjectorImpl implements HibernateQueryInjector {

    @Override
    public <R extends CriteriaQuery, T> void where(Querier querier, CriteriaBuilder criteriaBuilder, R criteriaQuery, Root<T> from) {
        final List<Predicate> predicates = new ArrayList<Predicate>();
        and(querier, criteriaBuilder, from, predicates);
        or(querier, criteriaBuilder, from, predicates);
        like(querier, criteriaBuilder, from, predicates);
        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
    }

    @Override
    public <T> void and(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(querier.and())) return;
        final List<Predicate> predicateList = new ArrayList<Predicate>();
        for (Map.Entry<String, Object> entry : querier.and().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry);
            predicateList.add(criteriaBuilder.equal(expression, entry.getValue()));
        }
        predicates.add(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
    }

    @Override
    public <T> void or(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(querier.or())) return;
        final List<Predicate> predicateList = new ArrayList<Predicate>();
        for (Map.Entry<String, Object> entry : querier.or().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry);
            predicateList.add(criteriaBuilder.equal(expression, entry.getValue()));
        }
        predicates.add(criteriaBuilder.or(predicateList.toArray(new Predicate[predicates.size()])));
    }

    @Override
    public <T> void like(Querier querier, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(querier.like())) return;
        for (Map.Entry<String, String> entry : querier.like().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry);
            predicates.add(criteriaBuilder.like(expression, String.format("%%%s%%", entry.getValue())));
        }
    }

    @Override
    public <T> void limit(TypedQuery<T> tTypedQuery, PageRequest request) {
        tTypedQuery.setFirstResult(request.getOffset()).setMaxResults(request.getPageSize());
    }

    @Override
    public <T> void orderby(Querier querier, CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<T> from) {
        if(Objects.isNull(querier.orderList()) || querier.orderList().isEmpty())
            return ;
        List<Order> orders = new ArrayList<Order>();
        for(cheetah.domain.Order order : querier.orderList().orders()) {
            if(order.getDirection() == cheetah.domain.Order.Direction.DESC)
                orders.add(criteriaBuilder.desc(from.get(order.getproperty())));
            else
                orders.add(criteriaBuilder.asc(from.get(order.getproperty())));
        }
        criteriaQuery.orderBy(orders);
    }

    @Override
    public <T> void groupby(AmpleQuerier querier, CriteriaQuery<?> criteriaQuery, Root<T> from) {
        if(StringUtils.isBlank(querier.groupby()))  return ;
        criteriaQuery.groupBy(from.get(querier.groupby()));
    }
}
