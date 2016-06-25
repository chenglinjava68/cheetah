package org.cheetah.commons.excel.processor;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.cheetah.commons.excel.ExcelException;
import org.cheetah.commons.excel.ExcelHeader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 该类实现了将一组对象转换为Excel表格，并且可以从Excel表格中读取到一组List对象中 该类利用了BeanUtils框架中的反射完成
 * 使用该类的前提，在相应的实体对象上通过ExcelReources来完成相应的注解
 *
 * @author Max
 */
public class TemplateProcessor<T> extends AbstractProcessor<T> {
    private ExcelTemplate excelTemplate;
    private Map<String, String> templateBasicData;

    TemplateProcessor() {
        super(true);
    }

    public TemplateProcessor(File templateFile, Map<String, String> basicData) throws IOException, InvalidFormatException {
        this();
        this.templateBasicData = basicData;
        workbook = WorkbookFactory.create(templateFile);
        sheet = workbook.getSheetAt(0);
        createOrFlushExcelTemplate();
    }

    public TemplateProcessor(InputStream templateInputStream, Map<String, String> basicData) throws IOException, InvalidFormatException {
        this();
        this.templateBasicData = basicData;
        workbook = WorkbookFactory.create(templateInputStream);
        sheet = workbook.getSheetAt(0);
        createOrFlushExcelTemplate();
    }

    public void createOrFlushExcelTemplate() {
        if (this.excelTemplate == null)
            this.excelTemplate = ExcelTemplate.newTemplate(workbook, sheet);
        else {
            this.excelTemplate.reset();
            this.excelTemplate.setWorkbook(workbook);
            this.excelTemplate.setSheet(sheet);
            this.excelTemplate.initTemplate();
        }
    }

    @Override
    public void export(OutputStream outputStream) {
        excelTemplate.writeToStream(outputStream);
    }
    @Override
    public void export(String filePath) {
        excelTemplate.writeToFile(filePath);
    }

    /**
     * 处理对象转换为Excel
     *
     * @param datas
     * @param clz
     * @return
     */
    @Override
    public void write(List<T> datas, Class<T> clz) {
        try {
            List<ExcelHeader> headers = ExcelResourcesHelper.getHeaderList(clz);
            Collections.sort(headers);
            for (T obj : datas) {
                excelTemplate.createNewRow();
                for (ExcelHeader eh : headers)
                    excelTemplate.createCell(BeanUtils.getProperty(obj, ExcelResourcesHelper.getTargetName(eh)));
            }
            excelTemplate.replaceFinalData(templateBasicData);
        } catch (Exception e) {
            throw new ExcelException(e);
        }
    }

}
