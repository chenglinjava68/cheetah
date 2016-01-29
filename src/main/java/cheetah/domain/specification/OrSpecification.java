package cheetah.domain.specification;

import cheetah.domain.Assembly;
import cheetah.domain.Entity;
import cheetah.domain.Specification;

/**
 * Created by Max on 2016/1/15.
 */
public class OrSpecification<T extends Entity> extends CompositeSpecification<T> {

    @Override
    public void assembleCriteria(Assembly assembly) {
        for (Specification<T> specification : getSpecifications()) {
            specification.assembleCriteria(assembly);
        }
    }

}
