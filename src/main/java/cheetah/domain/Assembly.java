package cheetah.domain;

import cheetah.domain.jpa.CriteriaBuilderAdapter;
import cheetah.domain.specification.GESpecification;

/**
 * Created by Max on 2016/1/16.
 */
public interface Assembly {

    void assemble();

    void registerAdapter(CriteriaBuilderAdapter adapter);

    void registerSpecification(GESpecification tgeSpecification);
}
