package cheetah.domain.specification;

import cheetah.domain.Assembly;
import cheetah.domain.Entity;
import cheetah.domain.Specification;

import java.io.Serializable;

/**
 * Created by Max on 2016/1/16.
 */
public abstract class AbstractSpecification<T extends Entity, V extends Serializable> implements Specification<T> {
    private V value;
    private String property;
    private Operator operator;

    public AbstractSpecification(V value, String property, Operator operator) {
        this.value = value;
        this.property = property;
        this.operator = operator;
    }

    public V getValue() {
        return value;
    }

    public String getProperty() {
        return property;
    }

    public Operator getOperator() {
        return operator;
    }

    public abstract void assembleCriteria(Assembly assembly);

    public abstract ComparisonType supportComparisonType();
}
