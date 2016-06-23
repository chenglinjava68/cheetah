package org.cheetah.commons.excel;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 该类实现了将一组对象转换为Excel表格，并且可以从Excel表格中读取到一组List对象中 该类利用了BeanUtils框架中的反射完成
 * 使用该类的前提，在相应的实体对象上通过ExcelReources来完成相应的注解
 *
 * @author Max
 */
public class ExcelProcessor<T> {
    private ExcelTemplate excelTemplate;
    private Workbook workbook;
    private Sheet sheet;

    public ExcelProcessor() {
    }

    public ExcelProcessor(File templateFile) throws IOException, InvalidFormatException {
        workbook = WorkbookFactory.create(templateFile);
        sheet = workbook.getSheetAt(0);
        createOrFlushExcelTemplate();

    }

    public ExcelProcessor(InputStream templateInputStream) throws IOException, InvalidFormatException {
        workbook = WorkbookFactory.create(templateInputStream);
        sheet = workbook.getSheetAt(0);
        createOrFlushExcelTemplate();
    }

    public ExcelProcessor<T> buildTemplate(File templateFile) throws IOException, InvalidFormatException {
        workbook = WorkbookFactory.create(templateFile);
        sheet = workbook.getSheetAt(0);
        createOrFlushExcelTemplate();
        return this;
    }

    public ExcelProcessor<T> buildTemplate(InputStream templateInputStream) throws IOException, InvalidFormatException {
        workbook = WorkbookFactory.create(templateInputStream);
        sheet = workbook.getSheetAt(0);
        createOrFlushExcelTemplate();
        return this;
    }

    public void changeSheet(int sheet) {
        this.sheet = workbook.getSheetAt(sheet);
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

    public ExcelTemplate getExcelTemplate() {
        return excelTemplate;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void write(OutputStream outputStream) {
        excelTemplate.writeToStream(outputStream);
    }

    public void write(String filePath) {
        excelTemplate.writeToFile(filePath);
    }

    /**
     *------------------------------------------------------------------------------------------------------------------
     */

    /**
     * @param datas
     * @param clz
     * @param isXssf
     * @return
     */
    Workbook handlerObj2Excel(List<T> datas, Class<T> clz, boolean isXssf) {
        Workbook wb = null;
        try {
            if (isXssf) {
                wb = new XSSFWorkbook();
            } else {
                wb = new HSSFWorkbook();
            }
            Sheet sheet = wb.createSheet();
            Row r = sheet.createRow(0);
            List<ExcelHeader> headers = getHeaderList(clz);
            Collections.sort(headers);
            // 写标题
            for (int i = 0; i < headers.size(); i++) {
                r.createCell(i).setCellValue(headers.get(i).getTitle());
            }
            // 写数据
            Object obj = null;
            for (int i = 0; i < datas.size(); i++) {
                r = sheet.createRow(i + 1);
                obj = datas.get(i);
                for (int j = 0; j < headers.size(); j++) {
                    r.createCell(j).setCellValue(
                            BeanUtils.getProperty(obj,
                                    getMethodName(headers.get(j))));
                }
            }
        } catch (Exception e) {
            throw new ExcelException(e);
        }
        return wb;
    }


    List<T> handlerExcel2Objs(InputStream inputStream, Class<T> clz, int sheetIndex,
                              int readLine, int tailLine) {
        List<T> objs = null;
        try {
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet sheet = wb.getSheetAt(sheetIndex);
            Row row = sheet.getRow(readLine);
            objs = new ArrayList<T>();
            Map<Integer, String> maps = getHeaderMap(row, clz);
            if (maps == null || maps.size() <= 0)
                throw new ExcelException("要读取的Excel的格式不正确，检查是否设定了合适的行");
            for (int i = readLine + 1; i <= sheet.getLastRowNum() - tailLine; i++) {
                row = sheet.getRow(i);
                if (row == null) break;
                T obj = clz.newInstance();
                for (Cell c : row) {
                    int ci = c.getColumnIndex();
                    if (!maps.containsKey(ci))
                        break;
                    String mn = maps.get(ci).substring(3);
                    mn = mn.substring(0, 1).toLowerCase() + mn.substring(1);
                    BeanUtils.copyProperty(obj, mn, this.getCellValue(c));
                }
                objs.add(obj);
            }
        } catch (Exception e) {
            throw new ExcelException(e);
        }
        return objs;
    }

    String getCellValue(Cell c) {
        String o = null;
        switch (c.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                o = "";
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                o = String.valueOf(c.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                o = String.valueOf(c.getCellFormula());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(c)) {
                    double d = c.getNumericCellValue();
                    Date date = HSSFDateUtil.getJavaDate(d);
                    SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    o = dformat.format(date);
                } else {
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setGroupingUsed(false);// true时的格式：1,234,567,890
                    o = nf.format(c.getNumericCellValue());// 数值类型的数据为double，所以需要转换一下
                }
                break;
            case Cell.CELL_TYPE_STRING:
                o = c.getStringCellValue();
                break;
            default:
                o = null;
                break;
        }
        return o;
    }

    Map<Integer, String> getHeaderMap(Row titleRow, Class clz) {
        List<ExcelHeader> headers = getHeaderList(clz);
        Map<Integer, String> maps = new HashMap<Integer, String>();
        for (Cell c : titleRow) {
            String title = c.getStringCellValue();
            for (ExcelHeader eh : headers) {
                if (eh.getTitle().trim().equals(title.trim())) {
                    maps.put(c.getColumnIndex(),
                            eh.getMethodName().replace("get", "set"));
                    break;
                }
            }
        }
        return maps;
    }

    List<ExcelHeader> getHeaderList(Class clz) {
        List<ExcelHeader> headers = new ArrayList<ExcelHeader>();
        Method[] ms = clz.getDeclaredMethods();
        for (Method m : ms) {
            String mn = m.getName();
            if (mn.startsWith("get")) {
                if (m.isAnnotationPresent(ExcelResources.class)) {
                    ExcelResources er = m.getAnnotation(ExcelResources.class);
                    headers.add(new ExcelHeader(er.title(), er.order(), mn));
                }
            }
        }
        return headers;
    }

    /**
     * 根据标题获取相应的方法名称
     *
     * @param eh
     * @return
     */
    String getMethodName(ExcelHeader eh) {
        String mn = eh.getMethodName().substring(3);
        mn = mn.substring(0, 1).toLowerCase() + mn.substring(1);
        return mn;
    }

    /**
     * 处理对象转换为Excel
     *
     * @param datas
     * @param clz
     * @return
     */
    void handlerObj2Excel(List<T> datas, Class<T> clz) {
        try {
            List<ExcelHeader> headers = getHeaderList(clz);
            Collections.sort(headers);
            for (T obj : datas) {
                excelTemplate.createNewRow();
                for (ExcelHeader eh : headers)
                    excelTemplate.createCell(BeanUtils.getProperty(obj, getMethodName(eh)));
            }
        } catch (Exception e) {
            throw new ExcelException(e);
        }
    }
}
