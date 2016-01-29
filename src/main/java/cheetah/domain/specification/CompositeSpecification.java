package cheetah.domain.specification;

import cheetah.domain.Entity;
import cheetah.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 2016/1/11.
 */
public abstract class CompositeSpecification<T extends Entity> implements Specification<T> {

    private final List<Specification<T>> specifications = new ArrayList<Specification<T>>();

    public void add(Specification<T> specification) {
        this.specifications.add(specification);
    }

    public void remove(Specification<T> specification) {
        this.specifications.remove(specification);
    }

    public List<Specification<T>> getSpecifications() {
        return this.specifications;
    }

}
