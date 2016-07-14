package org.cheetah.commons.excel.processor;

import org.apache.poi.ss.usermodel.*;
import org.cheetah.commons.excel.ExcelException;
import org.cheetah.commons.excel.ExcelHeader;
import org.cheetah.commons.logger.Loggers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 该类实现了基于模板的导出 如果要导出序号，需要在excel中定义一个标识为sernums
 * <p>
 * 如果要替换信息，需要传入一个Map，这个map中存储着要替换信息的值，
 * 在excel中通过#来开头 要从哪一行那一列开始替换需要定义一个标识为datas。
 * <p>
 * 如果要设定相应的样式，可以在该行使用styles完成设定，此时所有此行都使用该样式。
 * <p>
 * 如果使用defaultStyls作为表示，表示默认样式，如果没有defaultStyles
 * 使用datas行作为默认样式
 *
 * @author Max
 */
public class ExcelTemplate {
    /**
     * 数据的初始化的列数
     */
    private int initColIndex;
    /**
     * 数据的初始化的行数
     */
    private int initRowIndex;
    /**
     * 当前列数
     */
    private int curColIndex;
    /**
     * 当前行数
     */
    private int curRowIndex;

    /**
     * 模板标记，该标记表示数据从这行开始插入
     */
    private final static String DATA_LINE = "datas";

    private final static String DAFAULT_STYLE = "defaultStyles";

    private final static String STYLE = "styles";

    private final static String SER_NUM = "sernums";
    /**
     * 最后一行数据的索引
     */
    private int lastRowInde;

    private Workbook workbook;
    private Sheet sheet;
    /**
     * 当前行对象
     */
    private Row curRow;

    /**
     * 默认样式
     */
    private CellStyle defaultStyle;

    /**
     * 默认行高
     */
    private float rowHeight;
    /**
     * 插入序号的列索引
     */
    private int serColIndex;

    private Map<Integer, CellStyle> styles;

    private ExcelTemplate() {
    }

    private ExcelTemplate(Workbook workbook, Sheet sheet) {
        this.workbook = workbook;
        this.sheet = sheet;
        initTemplate();
    }

    public static ExcelTemplate newTemplate(Workbook workbook, Sheet sheet) {
        return new ExcelTemplate(workbook, sheet);
    }


    public static ExcelTemplate newTemplate() {
        return new ExcelTemplate();
    }

    /**
     * 写进一个文件
     *
     * @param filePath
     */
    public void writeToFile(String filePath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            workbook.write(fos);
        } catch (FileNotFoundException e) {
            Loggers.me().error(this.getClass(), "写入的文件不存在！", e);
            throw new ExcelException("写入的文件不存在！", e);
        } catch (IOException e) {
            Loggers.me().error(this.getClass(), "写入数据失败！", e);
            throw new ExcelException("写入数据失败！", e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 写到流中
     *
     * @param os
     */
    public void writeToStream(OutputStream os) {
        try {
            workbook.write(os);
        } catch (IOException e) {
            Loggers.me().error(this.getClass(), "写入流失败！", e);
            throw new ExcelException("写入流失败！" + e.getMessage());
        }
    }

    public void createCell(Object obj, ExcelHeader eh) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Cell c = curRow.createCell(curColIndex);
        CellValueConverter.setValue(c, obj, eh);
        setCellStyle(c);
        curColIndex++;
    }

    /**
     * 创建一个单元格，然后根据传来的value插入该单元格
     *
     * @param value
     */
    public void createCell(String value) {
        Cell c = curRow.createCell(curColIndex);
        c.setCellValue(value);
        setCellStyle(c);
        curColIndex++;
    }

    public void createCell(int value) {
        Cell c = curRow.createCell(curColIndex);
        setCellStyle(c);
        c.setCellValue((int) value);
        curColIndex++;
    }

    public void createCell(Date value) {
        Cell c = curRow.createCell(curColIndex);
        setCellStyle(c);
        c.setCellValue(value);
        curColIndex++;
    }

    public void createCell(double value) {
        Cell c = curRow.createCell(curColIndex);
        setCellStyle(c);
        c.setCellValue(value);
        curColIndex++;
    }

    public void createCell(boolean value) {
        Cell c = curRow.createCell(curColIndex);
        setCellStyle(c);
        c.setCellValue(value);
        curColIndex++;
    }

    public void createCell(Calendar value) {
        Cell c = curRow.createCell(curColIndex);
        setCellStyle(c);
        c.setCellValue(value);
        curColIndex++;
    }

    /**
     * 给列添加样式
     *
     * @param c
     */
    public void setCellStyle(Cell c) {
        if (styles != null && styles.containsKey(curColIndex)) {
            c.setCellStyle(styles.get(curColIndex));
        } else {
            c.setCellStyle(defaultStyle);
        }
    }

    /**
     * 创建一行
     */
    public void createNewRow() {
        lastRowInde = sheet.getLastRowNum();
        if (lastRowInde > curRowIndex && curRowIndex != initRowIndex) {
            sheet.shiftRows(curRowIndex, lastRowInde, 1, true, true);
        }
        curRow = sheet.createRow(curRowIndex);
        curRow.setHeightInPoints(rowHeight);
        curRowIndex++;
        curColIndex = initColIndex;
    }

    /**
     * 初始化模板
     */
    public void initTemplate() {
        initConfigData();
        curRow = sheet.createRow(curRowIndex);
    }

    /**
     * 初始化数据信息
     */
    private void initConfigData() {
        boolean findData = false;
        boolean findSer = false;
        for (Row row : sheet) {
            for (Cell c : row) {
                if (c.getCellType() != Cell.CELL_TYPE_STRING)
                    continue;
                String str = c.getStringCellValue().trim();
                if (str.equals(SER_NUM)) {
                    serColIndex = c.getColumnIndex();
                    findSer = true;
                }
                if (str.equals(DATA_LINE)) {
                    initColIndex = c.getColumnIndex();
                    initRowIndex = c.getRowIndex();
                    curColIndex = initColIndex;
                    curRowIndex = initRowIndex;
                    rowHeight = row.getHeightInPoints();
                    initStyles();
                    findData = true;
                    break;
                }
            }
        }
        if (!findSer) {
            initSer();
        }
    }

    /**
     * 遍历模板找到序号
     */
    private void initSer() {
        for (Row row : sheet) {
            for (Cell c : row) {
                if (c.getCellType() != Cell.CELL_TYPE_STRING)
                    continue;
                String str = c.getStringCellValue().trim();
                if (str.equals(SER_NUM)) {
                    serColIndex = c.getColumnIndex();
                }
            }
        }
    }

    /**
     * 插入序号
     */
    public void insertSer() {
        int index = 1;
        Row row = null;
        Cell c = null;
        for (int i = initRowIndex; i < curRowIndex; i++) {
            row = sheet.getRow(i);
            c = row.createCell(serColIndex);
            setCellStyle(c);
            c.setCellValue(index++);
        }
    }

    /**
     * 获取每个单元格的样式，存进Map中，key为列的索引，value为样式
     */
    private void initStyles() {
        styles = new HashMap<Integer, CellStyle>();
        for (Row row : sheet) {
            for (Cell c : row) {
                if (c.getCellType() != Cell.CELL_TYPE_STRING)
                    continue;
                String str = c.getStringCellValue().trim();
                if (str.equals(DAFAULT_STYLE)) {
                    defaultStyle = c.getCellStyle();
                }
                if (str.equals(STYLE)) {
                    styles.put(c.getColumnIndex(), c.getCellStyle());
                }
            }
        }
    }

    /**
     * 根据map替换相应的常量
     *
     * @param datas
     */
    public void replaceFinalData(Map<String, String> datas) {
        if(datas == null || datas.isEmpty())
            return;
        for (Row row : sheet) {
            for (Cell c : row) {
                if (c.getCellType() != Cell.CELL_TYPE_STRING)
                    continue;
                String str = c.getStringCellValue().trim();
                if (str.startsWith("#")) {
                    if (datas.containsKey(str.substring(1))) {
                        c.setCellValue(datas.get(str.substring(1)));
                }
                }
            }
        }
    }

    /**
     * 基于Properties的替换，依然也是替换#开始的
     */
    public void replaceFinalData(Properties prop) {
        if (prop == null || prop.isEmpty()) return;
        for (Row row : sheet) {
            for (Cell c : row) {
                if (c.getCellType() != Cell.CELL_TYPE_STRING)
                    continue;
                String str = c.getStringCellValue().trim();
                if (str.startsWith("#")) {
                    if (prop.containsKey(str.substring(1))) {
                        c.setCellValue(prop.getProperty(str.substring(1)));
                    }
                }
            }
        }
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public void reset() {
        this.initColIndex = 0;
        this.initRowIndex = 0;
        this.curColIndex = 0;
        this.curRowIndex = 0;
        this.lastRowInde = 0;
        this.sheet = null;
        this.curRow = null;
        this.defaultStyle = null;
        this.rowHeight = 0;
        this.serColIndex = 0;
        this.styles = null;
    }

}
