package cheetah.domain.jpa;

import cheetah.domain.AmpleEnquirer;
import cheetah.domain.PageRequest;
import cheetah.domain.Enquirer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * Created by Max on 2016/1/10.
 */
public final class JpaQueryAgreedImpl {

    @Override
    public <R extends CriteriaQuery, T> void where(Enquirer enquirer, CriteriaBuilder criteriaBuilder, R criteriaQuery, Root<T> from) {
        if(!enquirer.hasWhere()) return ;
        final List<Predicate> predicates = new ArrayList<Predicate>();
        and(enquirer, criteriaBuilder, from, predicates);
        or(enquirer, criteriaBuilder, from, predicates);
        in(enquirer, criteriaBuilder, from, predicates);
        notIn(enquirer, criteriaBuilder, from, predicates);
        gt(enquirer, criteriaBuilder, from, predicates);
        lt(enquirer, criteriaBuilder, from, predicates);
        ge(enquirer, criteriaBuilder, from, predicates);
        le(enquirer, criteriaBuilder, from, predicates);
        between(enquirer, criteriaBuilder, from, predicates);
        like(enquirer, criteriaBuilder, from, predicates);
        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
    }

    @Override
        public <T> void and(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
            if (CollectionUtils.isEmpty(enquirer.getAnd())) return;
            final List<Predicate> predicateList = new ArrayList<Predicate>();
            for (Map.Entry<String, Object> entry : enquirer.getAnd().entrySet()) {
                Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
                predicateList.add(criteriaBuilder.equal(expression, entry.getValue()));
            }
            predicates.add(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
        }

        @Override
        public <T> void or(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
            if (CollectionUtils.isEmpty(enquirer.getOr())) return;
            final List<Predicate> predicateList = new ArrayList<Predicate>();
            for (Map.Entry<String, Object> entry : enquirer.getOr().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
            predicateList.add(criteriaBuilder.equal(expression, entry.getValue()));
        }
        predicates.add(criteriaBuilder.or(predicateList.toArray(new Predicate[predicates.size()])));
    }

    @Override
    public <T> void in(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(enquirer.getIn())) return;
        for (Map.Entry<String, List<Object>> entry : enquirer.getIn().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
            CriteriaBuilder.In in = criteriaBuilder.in(expression);
            Iterator<Object> iter = entry.getValue().iterator();
            while (iter.hasNext()) {
                in.value(iter.next());
            }
            predicates.add(in);
        }
    }

    @Override
    public <T> void notIn(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(enquirer.getNotIn())) return;
        for (Map.Entry<String, List<Object>> entry : enquirer.getNotIn().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
            CriteriaBuilder.In in = criteriaBuilder.in(expression);
            Iterator<Object> iter = entry.getValue().iterator();
            while (iter.hasNext()) {
                in.value(iter.next());
            }
            predicates.add(criteriaBuilder.not(in));
        }

    }

    @Override
    public <T> void gt(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(enquirer.getGt())) return;
        final List<Predicate> predicateList = new ArrayList<Predicate>();
        for (Map.Entry<String, Number> entry : enquirer.getGt().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
            predicateList.add(criteriaBuilder.gt(expression, entry.getValue()));
        }
        predicates.addAll(predicateList);
    }

    @Override
    public <T> void lt(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(enquirer.getLt())) return;
        final List<Predicate> predicateList = new ArrayList<Predicate>();
        for (Map.Entry<String, Number> entry : enquirer.getLt().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
            predicateList.add(criteriaBuilder.lt(expression, entry.getValue()));
        }
        predicates.addAll(predicateList);
    }

    @Override
    public <T> void ge(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(enquirer.getGe())) return;
        final List<Predicate> predicateList = new ArrayList<Predicate>();
        for (Map.Entry<String, Number> entry : enquirer.getGe().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
            predicateList.add(criteriaBuilder.ge(expression, entry.getValue()));
        }
        predicates.addAll(predicateList);
    }

    @Override
    public <T> void le(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(enquirer.getLe())) return;
        final List<Predicate> predicateList = new ArrayList<Predicate>();
        for (Map.Entry<String, Number> entry : enquirer.getLe().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
            predicateList.add(criteriaBuilder.le(expression, entry.getValue()));
        }
        predicates.addAll(predicateList);
    }

    @Override
    public <T> void between(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (Objects.isNull(enquirer.getBetween())) return;
        Expression expression = QueryHelper.fieldProcessing(from, enquirer.getBetween().getName());
        if (enquirer.getBetween().getStart() instanceof String)
            predicates.add(criteriaBuilder.between(expression, (String) enquirer.getBetween().getStart(), (String) enquirer.getBetween().getEnd()));
        else if (enquirer.getBetween().getStart() instanceof Long)
            predicates.add(criteriaBuilder.between(expression, (Long) enquirer.getBetween().getStart(), (Long) enquirer.getBetween().getEnd()));
        else if (enquirer.getBetween().getStart() instanceof Integer)
            predicates.add(criteriaBuilder.between(expression, (Integer) enquirer.getBetween().getStart(), (Integer) enquirer.getBetween().getEnd()));
        else if (enquirer.getBetween().getStart() instanceof Short)
            predicates.add(criteriaBuilder.between(expression, (Short) enquirer.getBetween().getStart(), (Short) enquirer.getBetween().getEnd()));
        else if (enquirer.getBetween().getStart() instanceof Float)
            predicates.add(criteriaBuilder.between(expression, (Float) enquirer.getBetween().getStart(), (Float) enquirer.getBetween().getEnd()));
        else if (enquirer.getBetween().getStart() instanceof Double)
            predicates.add(criteriaBuilder.between(expression, (Double) enquirer.getBetween().getStart(), (Double) enquirer.getBetween().getEnd()));
    }

    @Override
    public <T> void like(Enquirer enquirer, CriteriaBuilder criteriaBuilder, Root<T> from, List<Predicate> predicates) {
        if (CollectionUtils.isEmpty(enquirer.getLike())) return;
        for (Map.Entry<String, String> entry : enquirer.getLike().entrySet()) {
            Expression expression = QueryHelper.fieldProcessing(from, entry.getKey());
            predicates.add(criteriaBuilder.like(expression, String.format("%%%s%%", entry.getValue())));
        }
    }

    @Override
    public <T> void limit(TypedQuery<T> tTypedQuery, PageRequest request) {
        tTypedQuery.setFirstResult(request.getOffset()).setMaxResults(request.getPageSize());
    }

    @Override
    public <T> void orderby(Enquirer enquirer, CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<T> from) {
        if (Objects.isNull(enquirer.getOrderList()) || enquirer.getOrderList().isEmpty())
            return;
        List<Order> orders = new ArrayList<Order>();
        for (cheetah.domain.Order order : enquirer.getOrderList().orders()) {
            if (order.getDirection() == cheetah.domain.Order.Direction.DESC)
                orders.add(criteriaBuilder.desc(from.get(order.getproperty())));
            else
                orders.add(criteriaBuilder.asc(from.get(order.getproperty())));
        }
        criteriaQuery.orderBy(orders);
    }

    @Override
    public <T> void groupby(AmpleEnquirer enquirer, CriteriaQuery<?> criteriaQuery, Root<T> from) {
        if (StringUtils.isBlank(enquirer.groupby())) return;
        criteriaQuery.groupBy(from.get(enquirer.groupby()));
    }
}
