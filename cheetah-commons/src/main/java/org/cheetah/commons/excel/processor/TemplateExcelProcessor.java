package org.cheetah.commons.excel.processor;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.cheetah.commons.excel.ExcelException;
import org.cheetah.commons.excel.ExcelHeader;
import org.cheetah.commons.logger.Info;

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
 * 建议：
 *  如果数据行超过5000行以上的，建议使用SimpleExcelProssor处理，否则会非常缓慢
 *
 * @author Max
 */
public class TemplateExcelProcessor<T> extends AbstractExcelProcessor<T> {
    private ExcelTemplate excelTemplate;
    private Map<String, String> templatePlaceholder;

    TemplateExcelProcessor() {
        super(true);
    }

    public TemplateExcelProcessor(File templateFile, Map<String, String> templatePlaceholder) throws IOException, InvalidFormatException {
        this();
        this.templatePlaceholder = templatePlaceholder;
        workbook = WorkbookFactory.create(templateFile);
        sheet = workbook.getSheetAt(0);
        createOrFlushExcelTemplate();
    }

    public TemplateExcelProcessor(InputStream templateInputStream, Map<String, String> templatePlaceholder) throws IOException, InvalidFormatException {
        this();
        this.templatePlaceholder = templatePlaceholder;
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
        Info.log(this.getClass(), "write datas size :{}, entity {}", datas.size(), clz);
        try {
            List<ExcelHeader> headers = ExcelResourcesHelper.getHeaderList(clz);
            Collections.sort(headers);
            for (int i = 0; i < datas.size(); i++) {
                long start = System.currentTimeMillis();
                Info.log(this.getClass(), "row {}", i);
                excelTemplate.createNewRow();
                long createRowTime = System.currentTimeMillis();
                for (ExcelHeader eh : headers) {
                    excelTemplate.createCell(datas.get(i), eh);
                }
                Info.log(this.getClass(), "创建行使用时间{}，每写一行数据需要{}毫秒",createRowTime - start, System.currentTimeMillis() - createRowTime);
            }
            excelTemplate.replaceFinalData(templatePlaceholder);
        } catch (Exception e) {
            throw new ExcelException("往excel写入数据失败！", e);
        }
    }

}
