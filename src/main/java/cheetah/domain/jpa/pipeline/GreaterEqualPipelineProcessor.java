package cheetah.domain.jpa.pipeline;

import cheetah.domain.jpa.JpaPipelineProcessor;
import cheetah.domain.jpa.QueryHelper;
import cheetah.domain.specification.GESpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 * Created by Max on 2016/1/16.
 */
public class GreaterEqualPipelineProcessor extends JpaPipelineProcessor {

    public GreaterEqualPipelineProcessor(GESpecification geSpecification, CriteriaBuilder criteriaBuilder, Root root) {
        super(geSpecification, criteriaBuilder, root);
    }

    @Override
    public void process() {
        Expression expression = QueryHelper.fieldProcessing(root, geSpecification.property());
        criteriaBuilder.ge(expression, (Number) geSpecification.value());
    }
}
