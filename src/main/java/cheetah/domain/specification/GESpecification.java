package cheetah.domain.specification;

import cheetah.domain.Assembly;
import cheetah.domain.Entity;

import java.io.Serializable;

/**
 * Created by Max on 2016/1/10.
 */
public class GESpecification<T extends Entity, V extends Serializable> extends AbstractSpecification<T, V> {

    public GESpecification(V value, String property, Operator operator) {
        super(value, property, operator);
    }

    @Override
    public void assembleCriteria(Assembly assembly) {
        assembly.registerSpecification(this);
        assembly.assemble();
    }

    @Override
    public ComparisonType supportComparisonType() {
        return ComparisonType.GREATER_EQUAL;
    }
}
