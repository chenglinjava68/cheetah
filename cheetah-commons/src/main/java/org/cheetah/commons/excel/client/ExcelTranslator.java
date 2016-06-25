package org.cheetah.commons.excel.client;

import org.cheetah.commons.ExcelProcessor;
import org.cheetah.commons.excel.ExcelException;
import org.cheetah.commons.excel.processor.SimpleProcessor;
import org.cheetah.commons.excel.processor.TemplateProcessor;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Max on 2016/6/25.
 */
public class ExcelTranslator<T> {
    private SimpleProcessor<T> readProcessor = new SimpleProcessor<>();

    public List<T> translator(InputStream inputStream, Class<T> clz) {
        return readProcessor.read(inputStream, clz, 0, 0, 0);
    }

    public List<T> translator(InputStream inputStream, Class<T> clz, int sheetIndex) {
        return readProcessor.read(inputStream, clz, sheetIndex, 0, 0);
    }

    public List<T> translator(InputStream inputStream, Class<T> clz, int sheetIndex,
                              int readLine, int tailLine) {
        return readProcessor.read(inputStream, clz, sheetIndex, readLine, tailLine);
    }

    public void translator(Translator<T> translator) {
        ExcelProcessor<T> processor;
        if (translator.hasTemplate()) {
            try {
                processor = new TemplateProcessor<>(translator.templateStream(), translator.basicData());
            } catch (Exception e) {
                e.printStackTrace();
                throw new ExcelException("创建模板异常", e);
            }
        } else
            processor = new SimpleProcessor<>();
        processor.write(translator.data(), translator.entity());
        processor.export(translator.toStream());
    }
}
