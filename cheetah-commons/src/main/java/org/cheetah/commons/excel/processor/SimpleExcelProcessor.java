package org.cheetah.commons.excel.processor;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.cheetah.commons.excel.ExcelException;
import org.cheetah.commons.excel.ExcelHeader;
import org.cheetah.commons.logger.Info;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

/**
 * 该类实现了将一组对象转换为Excel表格，并且可以从Excel表格中读取到一组List对象中 该类利用了BeanUtils框架中的反射完成
 * 使用该类的前提，在相应的实体对象上通过ExcelReources来完成相应的注解
 *
 * @author Max
 */
public class SimpleExcelProcessor<T> extends AbstractExcelProcessor<T> {

    public SimpleExcelProcessor() {
    }

    public SimpleExcelProcessor(boolean isXssf) {
        super(isXssf);
        if (isXssf) {
            workbook = new XSSFWorkbook();
        } else {
            workbook = new HSSFWorkbook();
        }
    }

    @Override
    public void export(OutputStream outputStream) {
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void export(String filePath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param datas
     * @param clz
     * @return
     */
    @Override
    public void write(List<T> datas, Class<T> clz) {
        Info.log(this.getClass(), "write datas size :{}, entity {}", datas.size(), clz);
        try {
            Sheet sheet = workbook.createSheet();
            Row r = sheet.createRow(0);
            List<ExcelHeader> headers = ExcelResourcesHelper.getHeaderList(clz);
            Collections.sort(headers);
            // 写标题
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = r.createCell(i);
                this.styleHandlers.forEach(o -> o.handle(cell, 0));
                cell.setCellValue(headers.get(i).getTitle());
            }
            // 写数据
            Object obj;
            for (int i = 0; i < datas.size(); i++) {
                r = sheet.createRow(i + 1);
                obj = datas.get(i);
                Info.log(this.getClass(), "row {}", i);
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = r.createCell(j);
                    final int rowIndex = i + 1;
                    this.styleHandlers.forEach(o -> o.handle(cell, rowIndex));
                    CellValueConverter.setValue(cell, obj, headers.get(j));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcelException(e);
        }
    }
}
