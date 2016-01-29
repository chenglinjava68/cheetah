package cheetah.domain.jpa;

import cheetah.domain.jpa.pipeline.PipelineProcessor;
import cheetah.domain.specification.GESpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

/**
 * Created by Max on 2016/1/16.
 */
public abstract class JpaPipelineProcessor implements PipelineProcessor {
    protected GESpecification geSpecification;
    protected CriteriaBuilder criteriaBuilder;
    protected Root root;

    public JpaPipelineProcessor(GESpecification geSpecification, CriteriaBuilder criteriaBuilder, Root root) {
        this.geSpecification = geSpecification;
        this.criteriaBuilder = criteriaBuilder;
        this.root = root;
    }

    public JpaPipelineProcessor geSpecification(GESpecification geSpecification) {
        this.geSpecification = geSpecification;
        return this;
    }

    public JpaPipelineProcessor criteriaBuilder(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
        return this;
    }

    public JpaPipelineProcessor root(Root root) {
        this.root = root;
        return this;
    }
}
