package cheetah.domain.specification;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Max on 2016/1/16.
 */
public class Specifications {
    private Collection<CompositeSpecification> compositeSpecifications = new ArrayList<CompositeSpecification>();
    private Specifications() {}

    public Specifications add(CompositeSpecification compositeSpecification) {
        this.add(compositeSpecification);
        return this;
    }

    public Specifications remove(CompositeSpecification compositeSpecification) {
        this.remove(compositeSpecification);
        return this;
    }

    public Collection<CompositeSpecification> get() {
        return compositeSpecifications;
    }

    public static Specifications begin() {
        return new Specifications();
    }
}
