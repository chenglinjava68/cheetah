package cheetah.domain.jpa;

import cheetah.domain.Assembly;
import cheetah.domain.jpa.pipeline.JpaPipelineProcessorFactory;
import cheetah.domain.jpa.pipeline.PipelineProcessorFactory;
import cheetah.domain.specification.GESpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;


/**
 * Created by Max on 2016/1/16.
 */
public class JpaAssembly implements Assembly {
    private CriteriaBuilderAdapter adapter;
    private CriteriaBuilder criteriaBuilder;
    private Root root;

    public JpaAssembly(CriteriaBuilder criteriaBuilder, Root root) {
        this.criteriaBuilder = criteriaBuilder;
        this.root = root;
    }

    @Override
    public void assemble() {
        adapter.injection();
    }

    @Override
    public void registerAdapter(CriteriaBuilderAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void registerSpecification(GESpecification tgeSpecification) {
        PipelineProcessorFactory factory = JpaPipelineProcessorFactory.getFactory(tgeSpecification,criteriaBuilder, root);
        switch (tgeSpecification.supportComparisonType()) {
            case BETWEEN:
                break;
            case EQUAL:
                this.adapter = new CriteriaBuilderAdapter(factory.createGreaterEqualPipelineProcessor());

                break;
            case FUZZY:
                break;
            case GREATER:
                break;
            case GREATER_EQUAL:
                break;
            case IN:
                break;
            case LOWER:
                break;
            case LOWER_EQUAL:
                break;
            case NOT_EQUAL:
                break;
            case NOT_IN:
                break;
            case SORT:
                break;
            default:
        }
    }


    public CriteriaBuilder criteriaBuilder() {
        return criteriaBuilder;
    }

    public Root root() {
        return root;
    }
}
