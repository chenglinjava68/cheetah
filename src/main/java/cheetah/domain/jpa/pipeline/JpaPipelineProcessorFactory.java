package cheetah.domain.jpa.pipeline;

import cheetah.domain.specification.GESpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

/**
 * Created by Max on 2016/1/16.
 */
public class JpaPipelineProcessorFactory implements PipelineProcessorFactory {
    protected GESpecification geSpecification;
    protected CriteriaBuilder criteriaBuilder;
    protected Root root;

    public JpaPipelineProcessorFactory(GESpecification geSpecification, CriteriaBuilder criteriaBuilder, Root root) {
        this.geSpecification = geSpecification;
        this.criteriaBuilder = criteriaBuilder;
        this.root = root;
    }

    public static PipelineProcessorFactory getFactory(GESpecification geSpecification, CriteriaBuilder criteriaBuilder, Root root) {
        return new JpaPipelineProcessorFactory(geSpecification, criteriaBuilder, root);
    }

    @Override
    public GreaterEqualPipelineProcessor createGreaterEqualPipelineProcessor() {
        return new GreaterEqualPipelineProcessor(geSpecification, criteriaBuilder, root);
    }

}
