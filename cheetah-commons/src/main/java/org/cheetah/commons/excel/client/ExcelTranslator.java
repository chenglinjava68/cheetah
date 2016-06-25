package org.cheetah.commons.excel.client;

import org.cheetah.commons.excel.ExcelProcessor;
import org.cheetah.commons.excel.ExcelException;
import org.cheetah.commons.excel.processor.SimpleProcessor;
import org.cheetah.commons.excel.processor.TemplateProcessor;

import java.io.InputStream;
import java.util.List;

/**
 * excel转换器
 * Created by Max on 2016/6/25.
 */
public class ExcelTranslator<T> {
    private SimpleProcessor<T> readProcessor = new SimpleProcessor<>();

    /**
     * 将excel转为数据
     * @param inputStream
     * @param clz
     * @return
     */
    public List<T> translator(InputStream inputStream, Class<T> clz) {
        return readProcessor.read(inputStream, clz, 0, 0, 0);
    }

    /**
     * 将excel转为数据
     * @param inputStream
     * @param clz
     * @param sheetIndex
     * @return
     */
    public List<T> translator(InputStream inputStream, Class<T> clz, int sheetIndex) {
        return readProcessor.read(inputStream, clz, sheetIndex, 0, 0);
    }

    /**
     * 将excel转为数据
     * @param inputStream excel文件流
     * @param clz   对应的实体Class
     * @param sheetIndex excel中sheet索引，起始为0
     * @param readLine 读的起始行
     * @param tailLine 是否有标志行尾不需要读取
     * @return
     */
    public List<T> translator(InputStream inputStream, Class<T> clz, int sheetIndex,
                              int readLine, int tailLine) {
        return readProcessor.read(inputStream, clz, sheetIndex, readLine, tailLine);
    }

    /**
     * 将数据转为excel文件流
     * @param translator
     */
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
