package cheetah.domain.jpa;

import cheetah.domain.jpa.pipeline.PipelineProcessor;

/**
 * Created by Max on 2016/1/16.
 */
public class CriteriaBuilderAdapter implements CriteriaPipeline {
    private PipelineProcessor processor;

    public CriteriaBuilderAdapter(PipelineProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void injection() {
        processor.process();
    }

    public CriteriaBuilderAdapter setProcessor(PipelineProcessor processor) {
        this.processor = processor;
        return this;
    }
}
